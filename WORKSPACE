workspace(name = "gameoflife")

git_repository(
    name = "grpc_java",
    remote = "https://github.com/grpc/grpc-java.git",
    tag = "v1.8.0",
)

maven_jar(
    name = "io_opencensus_api",
    artifact = "io.opencensus:opencensus-api:0.10.0",
)

maven_jar(
    name = "io_opencensus_impl_core",
    artifact = "io.opencensus:opencensus-impl-core:0.10.0",
)

maven_jar(
    name = "io_opencensus_impl",
    artifact = "io.opencensus:opencensus-impl:0.10.0",
)

maven_jar(
    name = "io_opencensus_contrib_zpages",
    artifact = "io.opencensus:opencensus-contrib-zpages:0.10.0",
)


maven_jar(
    name = "io_opencensus_exporter_stats_stackdriver",
    artifact = "io.opencensus:opencensus-exporter-stats-stackdriver:0.10.0",
)

maven_jar(
    name = "io_grpc_services",
    artifact = "io.grpc:grpc-services:1.8.0",
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
    artifact = "com.google.api-client:google-api-client:1.23.0",
)

#maven_jar(
#    name = "com_google_api_client_util",
#    artifact = "com.google.api.client:google-api-client-util:1.1.0-alpha",
#)

maven_jar(
    name = "com_google_api_http_client",
    artifact = "com.google.http-client:google-http-client:1.23.0",
)

maven_jar(
    name = "com_google_api_http_client_jackson2",
    artifact = "com.google.http-client:google-http-client-jackson2:1.23.0",
)

#maven_jar(
#    name = "com_google_api_client_auth_oauth2",
#    artifact = "com.google.api.client:google-api-client-auth-oauth2:1.2.3-alpha",
#)

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
    name = "io_grpc_auth",
    artifact = "io.grpc:grpc-auth:1.8.0",
)

load("@grpc_java//:repositories.bzl", "grpc_java_repositories")

grpc_java_repositories()

