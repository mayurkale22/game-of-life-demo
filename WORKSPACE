workspace(name = "gameoflife")

git_repository(
    name = "grpc_java",
    remote = "https://github.com/grpc/grpc-java.git",
    tag = "v1.8.0",
)

maven_jar(
    name = "io_opencensus_impl_core",
    artifact = "io.opencensus:opencensus-impl-core:0.9.0",
)

maven_jar(
    name = "io_opencensus_impl",
    artifact = "io.opencensus:opencensus-impl:0.9.0",
)

maven_jar(
    name = "io_opencensus_exporter_stats_stackdriver",
    artifact = "io.opencensus:opencensus-exporter-stats-stackdriver:0.9.1",
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
    name = "com_google_auth_credentials",
    artifact = "com.google.auth:google-auth-library-credentials:0.8.0",
)

maven_jar(
    name = "com_google_auth_oauth2_http",
    artifact = "com.google.auth:google-auth-library-oauth2-http:0.8.0",
)

maven_jar(
    name = "com_google_cloud_monitoring",
    artifact = "com.google.cloud:google-cloud-monitoring:0.30.0-beta",
)

maven_jar(
    name = "com_google_guava",
    artifact = "com.google.guava:guava:19.0",
)

maven_jar(
    name = "com_google_api_client",
    artifact = "com.google.api.client:google-api-client:1.4.1-beta",
)

maven_jar(
    name = "com_google_api_client_util",
    artifact = "com.google.api.client:google-api-client-util:1.2.0-alpha",
)

maven_jar(
    name = "com_google_api_client_auth_oauth2",
    artifact = "com.google.api.client:google-api-client-auth-oauth2:1.2.3-alpha",
)

load("@grpc_java//:repositories.bzl", "grpc_java_repositories")

grpc_java_repositories()

