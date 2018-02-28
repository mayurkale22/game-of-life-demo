workspace(name = "gameoflife")

opencensus_version = "0.12.0"
grpc_verion = "1.10.0"
google_cloud_api_version = "1.23.0"
prometheus_version = "0.2.0"

git_repository(
    name = "grpc_java",
    remote = "https://github.com/grpc/grpc-java.git",
    tag = "v" + grpc_verion,
)

maven_jar(
    name = "io_opencensus_opencensus_api",
    artifact = "io.opencensus:opencensus-api:" + opencensus_version,
)

maven_jar(
    name = "io_opencensus_opencensus_impl_core",
    artifact = "io.opencensus:opencensus-impl-core:" + opencensus_version,
)

maven_jar(
    name = "io_opencensus_opencensus_impl",
    artifact = "io.opencensus:opencensus-impl:" + opencensus_version,
)

maven_jar(
    name = "io_opencensus_opencensus_exporter_stats_prometheus",
    artifact = "io.opencensus:opencensus-exporter-stats-prometheus:" + opencensus_version,
)

maven_jar(
    name = "io_opencensus_opencensus_exporter_stats_stackdriver",
    artifact = "io.opencensus:opencensus-exporter-stats-stackdriver:" + opencensus_version,
)

maven_jar(
    name = "io_opencensus_opencensus_contrib_zpages",
    artifact = "io.opencensus:opencensus-contrib-zpages:" + opencensus_version,
)

maven_jar(
    name = "io_opencensus_opencensus_contrib_grpc_metrics",
    artifact = "io.opencensus:opencensus-contrib-grpc-metrics:" + opencensus_version,
)

maven_jar(
    name = "com_lmax_disruptor",
    artifact = "com.lmax:disruptor:3.3.6",
)

maven_jar(
    name = "com_google_api_gax",
    artifact = "com.google.api:gax:1.15.0",
)

maven_jar(
    name = "com_google_api_gax_grpc",
    artifact = "com.google.api:gax-grpc:1.15.0",
)

maven_jar(
    name = "com_google_auth_credentials",
    artifact = "com.google.auth:google-auth-library-credentials:0.9.0",
)

maven_jar(
    name = "com_google_auth_oauth2_http",
    artifact = "com.google.auth:google-auth-library-oauth2-http:0.9.0",
)

maven_jar(
    name = "com_google_cloud_core",
    artifact = "com.google.cloud:google-cloud-core:1.12.0",
)

maven_jar(
    name = "com_google_cloud_monitoring",
    artifact = "com.google.cloud:google-cloud-monitoring:0.30.0-beta",
)

maven_jar(
    name = "com_google_guava",
    artifact = "com.google.guava:guava:23.0",
)

maven_jar(
    name = "com_google_api_common",
    artifact = "com.google.api:api-common:1.2.0",
)

maven_jar(
    name = "com_google_api_client",
    artifact = "com.google.api-client:google-api-client:" + google_cloud_api_version,
)

maven_jar(
    name = "com_google_api_http_client",
    artifact = "com.google.http-client:google-http-client:" + google_cloud_api_version,
)

maven_jar(
    name = "com_google_api_http_client_jackson2",
    artifact = "com.google.http-client:google-http-client-jackson2:" + google_cloud_api_version,
)

maven_jar(
    name = "com_google_api_grpc_cloud_monitoring",
    artifact = "com.google.api.grpc:grpc-google-cloud-monitoring-v3:0.1.25",
)

maven_jar(
    name = "com_google_api_proto_cloud_monitoring",
    artifact = "com.google.api.grpc:proto-google-cloud-monitoring-v3:0.1.25",
)

maven_jar(
    name = "com_fasterxml_jackson_core_jackson_core",
    artifact = "com.fasterxml.jackson.core:jackson-core:2.1.3",
)

maven_jar(
    name = "org_threeten_threetenbp",
    artifact = "org.threeten:threetenbp:1.3.6",
)

maven_jar(
    name = "org_eclipse_jetty_alpn",
    artifact = "org.eclipse.jetty.alpn:alpn-api:1.1.3.v20160715",
)

maven_jar(
    name = "org_json",
    artifact = "org.json:json:20171018",
)

maven_jar(
    name = "io_netty_codec_http2",
    artifact = "io.netty:netty-codec-http2:5.0.0.Alpha2",
)

maven_jar(
    name = "io_netty_tcnative_boringssl_static",
    artifact = "io.netty:netty-tcnative-boringssl-static:2.0.7.Final",
)

maven_jar(
    name = "io_grpc_services",
    artifact = "io.grpc:grpc-services:" + grpc_verion,
)

maven_jar(
    name = "io_grpc_auth",
    artifact = "io.grpc:grpc-auth:" + grpc_verion,
)

maven_jar(
    name = "io_prometheus_simpleclient",
    artifact = "io.prometheus:simpleclient:" + prometheus_version,
)

maven_jar(
    name = "io_prometheus_simpleclient_httpserver",
    artifact = "io.prometheus:simpleclient_httpserver:" + prometheus_version,
)

maven_jar(
    name = "io_prometheus_simpleclient_common",
    artifact = "io.prometheus:simpleclient_common:" + prometheus_version,
)

load("@grpc_java//:repositories.bzl", "grpc_java_repositories")

grpc_java_repositories(
    omit_com_google_api_grpc_google_common_protos=False,
    omit_com_google_auth_google_auth_library_credentials=False,
    omit_com_google_code_findbugs_jsr305=False,
    omit_com_google_code_gson=False,
    omit_com_google_errorprone_error_prone_annotations=False,
    omit_com_google_guava=False,
    omit_com_google_protobuf=False,
    omit_com_google_protobuf_java=False,
    omit_com_google_protobuf_nano_protobuf_javanano=False,
    omit_com_google_truth_truth=False,
    omit_com_squareup_okhttp=False,
    omit_com_squareup_okio=False,
    omit_io_netty_buffer=False,
    omit_io_netty_common=False,
    omit_io_netty_transport=False,
    omit_io_netty_codec=False,
    omit_io_netty_codec_socks=False,
    omit_io_netty_codec_http=False,
    omit_io_netty_codec_http2=False,
    omit_io_netty_handler=False,
    omit_io_netty_handler_proxy=False,
    omit_io_netty_resolver=False,
    omit_io_netty_tcnative_boringssl_static=False,
    omit_io_opencensus_api=True, # Use the latest OpenCensus version
    omit_io_opencensus_grpc_metrics=True, # Use the latest OpenCensus version
    omit_junit_junit=False
)

