rootProject.name = "ecom"

pluginManagement {
    plugins {
        kotlin("jvm") version "2.0.10"
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include(":gateway")
include(":common")
include(":proto")
include("product")
include("grpc-mapper")
include("auth")
include("auth")
include("auth")
