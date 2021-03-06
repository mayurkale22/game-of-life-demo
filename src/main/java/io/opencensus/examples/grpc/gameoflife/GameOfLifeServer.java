/*
 * Copyright 2018, OpenCensus Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opencensus.examples.grpc.gameoflife;

import static io.opencensus.examples.grpc.gameoflife.GameOfLifeApplication.CALLER;
import static io.opencensus.examples.grpc.gameoflife.GameOfLifeApplication.CLIENT_TAG_KEY;
import static io.opencensus.examples.grpc.gameoflife.GameOfLifeApplication.METHOD;
import static io.opencensus.examples.grpc.gameoflife.GameOfLifeApplication.ORIGINATOR;
import static io.opencensus.examples.grpc.gameoflife.GolUtils.getPortOrDefaultFromArgs;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import io.opencensus.common.Duration;
import io.opencensus.common.Scope;
import io.opencensus.contrib.grpc.metrics.RpcMeasureConstants;
import io.opencensus.contrib.grpc.metrics.RpcViews;
import io.opencensus.contrib.zpages.ZPageHandlers;
import io.opencensus.exporter.stats.prometheus.PrometheusStatsCollector;
import io.opencensus.exporter.stats.stackdriver.StackdriverStatsConfiguration;
import io.opencensus.exporter.stats.stackdriver.StackdriverStatsExporter;
import io.opencensus.exporter.trace.logging.LoggingTraceExporter;
import io.opencensus.exporter.trace.stackdriver.StackdriverTraceConfiguration;
import io.opencensus.exporter.trace.stackdriver.StackdriverTraceExporter;
import io.opencensus.stats.Aggregation.Distribution;
import io.opencensus.stats.BucketBoundaries;
import io.opencensus.stats.Measure.MeasureDouble;
import io.opencensus.stats.Stats;
import io.opencensus.stats.StatsRecorder;
import io.opencensus.stats.View;
import io.opencensus.stats.View.AggregationWindow.Cumulative;
import io.opencensus.stats.View.Name;
import io.opencensus.stats.ViewManager;
import io.opencensus.tags.TagContext;
import io.opencensus.tags.TagKey;
import io.opencensus.tags.Tagger;
import io.opencensus.tags.Tags;
import io.opencensus.trace.Span;
import io.opencensus.trace.Status;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.Tracing;
import io.opencensus.trace.samplers.Samplers;
import io.prometheus.client.exporter.HTTPServer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

final class GameOfLifeServer {

  private final int port;

  private static final Logger logger = Logger.getLogger(GameOfLifeServer.class.getName());
  private static final Tagger tagger = Tags.getTagger();
  private static final StatsRecorder statsRecorder = Stats.getStatsRecorder();
  private static final ViewManager viewManager = Stats.getViewManager();
  private static final Tracer tracer = Tracing.getTracer();

  private static final TagKey SERVER_TAG_KEY = TagKey.create("server");
  private static final List<Double> bucketBoundaries = Arrays.asList(0.0, 5.0, 10.0, 15.0, 20.0);
  private static final MeasureDouble SERVER_MEASURE =
      MeasureDouble.create("gol_server_measure", "Sample measure for game of life server.", "1");
  private static final Cumulative CUMULATIVE = Cumulative.create();
  private static final Name SERVER_VIEW_NAME = Name.create("gol_server_view");
  private static final View SERVER_VIEW =
      View.create(
          SERVER_VIEW_NAME,
          "Sample view for game of life server.",
          SERVER_MEASURE,
          Distribution.create(BucketBoundaries.create(bucketBoundaries)),
          Arrays.asList(SERVER_TAG_KEY, CLIENT_TAG_KEY, CALLER, METHOD, ORIGINATOR),
          CUMULATIVE);
  private static final Name SERVER_LATENCY_VIEW_NAME = Name
      .create("gol_server_latency_custom_view");
  private static final View SERVER_LATENCY_VIEW =
      View.create(
          SERVER_LATENCY_VIEW_NAME,
          "Server latency view separated by server tags.",
          RpcMeasureConstants.RPC_SERVER_SERVER_LATENCY,
          Distribution.create(BucketBoundaries.create(bucketBoundaries)),
          Arrays.asList(CLIENT_TAG_KEY, CALLER, METHOD, ORIGINATOR),
          CUMULATIVE);

  private Server server;

  private static String executeCommand(String command) {
    Span span = tracer.getCurrentSpan();
    span.addAnnotation("Gol Server started calculating next generation.");
    String[] decode = command.split(" ");
    if (decode.length == 3 && decode[0].equals("gol")) {
      int gens = GolUtils.toInt(decode[1]);
      int dims = (int) Math.sqrt(decode[2].length());
      if (dims * dims == decode[2].length()) {
        // add server tag
        TagContext ctx = tagger.currentBuilder()
            .put(SERVER_TAG_KEY, GolUtils.getTagValue(dims, gens, "server"))
            .build();
        try (Scope scopedTags = tagger.withTagContext(ctx)) {
          // Record some random stats [0, 20) against server tags.
          statsRecorder.newMeasureMap()
              .put(SERVER_MEASURE, new Random().nextInt(20))
              .record();
          String result = new GameOfLife(dims, decode[2]).calcNextGenerations(gens).encode();
          span.addAnnotation("Gol Server finished calculating next generation.");
          return result;
        }
      }
    }
    span.addAnnotation("Gol Server received bad request.");
    span.setStatus(Status.INVALID_ARGUMENT);
    return "Error: bad request";
  }

  private void start() throws IOException {
    /* The port on which the server should run */
    server =
        ServerBuilder.forPort(port)
            .addService(new CommandProcessorImpl())
            .build()
            .start();
    logger.info("Server started, listening on " + port);

    Runtime.getRuntime()
        .addShutdownHook(
            new Thread() {
              @Override
              public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                GameOfLifeServer.this.stop();
                System.err.println("*** server shut down");
              }
            });
  }

  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon threads.
   */
  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  GameOfLifeServer(int port) {
    this.port = port;

    Tracing.getExportComponent()
        .getSampledSpanStore()
        .registerSpanNamesForCollection(Collections.singletonList("GolServerSpan"));
  }

  private static class CommandProcessorImpl extends CommandProcessorGrpc.CommandProcessorImplBase {

    @Override
    public void execute(CommandRequest req, StreamObserver<CommandResponse> responseObserver) {
      // Create one span on server side for each incoming RPC.
      try (Scope scopedSpan =
          tracer.spanBuilder("GolServerSpan")
              .setRecordEvents(true)
              .setSampler(Samplers.alwaysSample())
              .startScopedSpan()) {
        Span span = tracer.getCurrentSpan();
        span.addAnnotation("Gol Server received request.");
        CommandResponse reply =
            CommandResponse.newBuilder().setRetval(executeCommand(req.getReq())).build();
        span.addAnnotation("Gol Server is sending response.");
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
        span.addAnnotation("Gol Server sent response.");
      }
    }
  }

  /**
   * Main launches the server from the command line.
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    int serverPort = getPortOrDefaultFromArgs(args, 0, 3000);
    int serverZPagePort = getPortOrDefaultFromArgs(args, 1, 9000);
    String cloudProjectId = null;
    if (args.length >= 3) {
      cloudProjectId = args[2];
    }
    int prometheusPort = getPortOrDefaultFromArgs(args, 3, 10000);

    viewManager.registerView(SERVER_VIEW);
    viewManager.registerView(SERVER_LATENCY_VIEW);
    RpcViews.registerAllViews();
    Tracing.getExportComponent().getSampledSpanStore().registerSpanNamesForCollection(
        Arrays.asList("GolServerSpan", "GolClientSpan", "GolClientChildSpan"));

    ZPageHandlers.startHttpServerAndRegisterAll(serverZPagePort);

    if (cloudProjectId != null) {
      StackdriverStatsExporter.createAndRegister(
          StackdriverStatsConfiguration.builder()
              .setProjectId(cloudProjectId)
              .setExportInterval(Duration.create(5, 0))
              .build());
      StackdriverTraceExporter.createAndRegister(
          StackdriverTraceConfiguration.builder().setProjectId(cloudProjectId).build());
    }

    // ZipkinTraceExporter.createAndRegister("http://127.0.0.1:9411/api/v2/spans", "Service");
    LoggingTraceExporter.register();
    PrometheusStatsCollector.createAndRegister();
    HTTPServer prometheusServer = new HTTPServer(prometheusPort, true);

    GameOfLifeServer server = new GameOfLifeServer(serverPort);
    server.start();
    server.blockUntilShutdown();
  }
}
