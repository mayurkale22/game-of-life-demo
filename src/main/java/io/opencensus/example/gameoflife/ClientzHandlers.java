/*
 * Copyright 2017, OpenCensus Authors
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

package io.opencensus.example.gameoflife;

import static com.google.common.base.Preconditions.checkState;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

final class ClientzHandlers {

  // The HttpServer listening socket backlog (maximum number of queued incoming connections).
  private static final int BACKLOG = 5;
  // How many seconds to wait for the HTTP server to stop.
  private static final int STOP_DELAY = 1;
  private static final Logger logger = Logger.getLogger(ClientzHandlers.class.getName());
  private static final String CLIENTZ_URL = "/clientz";
  private static final ClientzHandler clientzHandler = new ClientzHandler("localhost", 3000, 32);

  private static final Object monitor = new Object();

  @GuardedBy("monitor")
  private static HttpServer server;

  /**
   * Registers all pages to the given {@code HttpServer}.
   *
   * @param server the server that exports the tracez page.
   */
  public static void registerAllToHttpServer(HttpServer server) {
    server.createContext(CLIENTZ_URL, clientzHandler);
  }

  /**
   * Starts an {@code HttpServer} and registers all pages to it. When the JVM shuts down the server
   * is stopped.
   *
   * <p>Users must call this function only once per process.
   *
   * @param port the port used to bind the {@code HttpServer}.
   * @throws IllegalStateException if the server is already started.
   * @throws IOException if the server cannot bind to the requested address.
   */
  public static void startHttpServerAndRegisterAll(int port) throws IOException {
    synchronized (monitor) {
      checkState(server == null, "The HttpServer is already started.");
      server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
      ClientzHandlers.registerAllToHttpServer(server);
      server.start();
      logger.fine("HttpServer started on address " + server.getAddress().toString());
    }

    // This does not need to be mutex protected because it is guaranteed that only one thread will
    // get ever here.
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread() {
              @Override
              public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                logger.fine("*** Shutting down client hook (JVM shutting down)");
                ClientzHandlers.stop();
                logger.fine("*** Client hook shut down");
              }
            });
  }

  private static void stop() {
    synchronized (monitor) {
      // This should never happen because we register the shutdown hook only if we start the server.
      checkState(server != null, "The HttpServer is already stopped.");
      server.stop(STOP_DELAY);
      server = null;
    }
  }
}
