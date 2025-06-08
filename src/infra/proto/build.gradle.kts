plugins {
    kotlin("jvm")
    java
    id("com.google.protobuf") version "0.9.4"
}

group = "io.kuz"
version = "unspecified"

dependencies {
    api("com.google.protobuf:protobuf-java:4.28.2")
    implementation("io.grpc:grpc-protobuf:1.64.0")
    implementation("io.grpc:grpc-stub:1.64.0")
    implementation("io.grpc:grpc-kotlin-stub:1.4.1")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.3"
    }
    plugins {
        maybeCreate("grpc").apply {
            artifact = "io.grpc:protoc-gen-grpc-java:1.64.0"
        }
        maybeCreate("grpckt").apply {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                maybeCreate("java")
            }
            it.plugins {
                maybeCreate("grpc")
                maybeCreate("grpckt")
            }
        }
    }
}

sourceSets {
    main {
        proto.srcDir("src/main/proto")
    }
}

tasks.named<ProcessResources>("processResources") {
    exclude("**/*.proto") // ⛔ prevent .proto files from being copied into resources
}