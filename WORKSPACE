workspace(name = "gameoflife")

grpc_verion = "1.9.0"
google_cloud_api_version = "1.23.0"

git_repository(
    name = "grpc_java",
    remote = "https://github.com/grpc/grpc-java.git",
    tag = "v" + grpc_verion,
)

maven_jar(
    name = "com_google_auth_credentials",
    artifact = "com.google.auth:google-auth-library-credentials:0.9.0",
)

load("//:gameoflife_workspace.bzl", "gameoflife_maven_jars")
load("@grpc_java//:repositories.bzl", "grpc_java_repositories")

gameoflife_maven_jars()
grpc_java_repositories(
    # Omit to avoid conflicts.

    # Remove this one after gRPC v1.10.0.
    omit_com_google_instrumentation_api=True,

    # Uncomment this one after gRPC v1.10.0.
    # omit_com_google_auth_google_auth_library_credentials=True,

    omit_com_google_api_grpc_google_common_protos=True,
    omit_com_google_code_findbugs_jsr305=True,
    omit_com_google_code_gson=True,
    omit_com_google_errorprone_error_prone_annotations=True,
    omit_com_google_guava=True,
    omit_com_google_protobuf=True,
    omit_com_google_protobuf_java=True,
    omit_com_google_protobuf_nano_protobuf_javanano=True,
    omit_com_google_truth_truth=True,
    omit_com_squareup_okhttp=True,
    omit_com_squareup_okio=True,

    # Import netty dependencies.
    omit_io_netty_buffer=False,
    omit_io_netty_common=False,
    omit_io_netty_transport=False,
    omit_io_netty_codec=False,
    omit_io_netty_codec_socks=False,
    omit_io_netty_codec_http=False,
    omit_io_netty_handler=False,
    omit_io_netty_resolver=False,

    # These 3 netty dependencies have already been included in opencensus_workspace.bzl
    omit_io_netty_handler_proxy=True,
    omit_io_netty_codec_http2=True,
    omit_io_netty_tcnative_boringssl_static=True,

    omit_io_opencensus_api=True,
    omit_io_opencensus_grpc_metrics=True,
    omit_junit_junit=True
)

# proto_library, cc_proto_library, and java_proto_library rules implicitly
# depend on @com_google_protobuf for protoc and proto runtimes.
# This statement defines the @com_google_protobuf repo.
http_archive(
    name = "com_google_protobuf",
    sha256 = "1f8b9b202e9a4e467ff0b0f25facb1642727cdf5e69092038f15b37c75b99e45",
    strip_prefix = "protobuf-3.5.1",
    urls = ["https://github.com/google/protobuf/archive/v3.5.1.zip"],
)

# Remove this one after gRPC v1.10.0.
# See https://github.com/grpc/grpc-java/issues/3175.
http_archive(
    name = "com_google_protobuf_java",
    sha256 = "1f8b9b202e9a4e467ff0b0f25facb1642727cdf5e69092038f15b37c75b99e45",
    strip_prefix = "protobuf-3.5.1",
    urls = ["https://github.com/google/protobuf/archive/v3.5.1.zip"],
)

