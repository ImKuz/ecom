plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.application)
}

group = "io.kuz"
version = "unspecified"

dependencies {
    implementation(project(":proto"))
    implementation(project(":common"))
    implementation(project(":grpc-mapper"))
    implementation(libs.grpc.kotlin.stub)
    implementation(libs.grpc.netty)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.postgresql)
    implementation(libs.hikaricp)
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
    implementation(libs.spring.context)
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("io.kuz.ecom.product.ProductServiceApplicationKt")
}
