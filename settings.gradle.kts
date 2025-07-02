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

include(
    ":common",
    ":services:gateway",
    ":services:auth",
    ":services:product",
    ":services:shop",
    ":infra:proto",
    ":infra:grpc-mapper",
    ":infra:spring-helpers"
)

project(":common").projectDir = file("src/common")
project(":services:gateway").projectDir = file("src/services/gateway")
project(":services:auth").projectDir = file("src/services/auth")
project(":services:product").projectDir = file("src/services/product")
project(":services:shop").projectDir = file("src/services/shop")
project(":infra:proto").projectDir = file("src/infra/proto")
project(":infra:grpc-mapper").projectDir = file("src/infra/grpc-mapper")
project(":infra:spring-helpers").projectDir = file("src/infra/spring-helpers")
