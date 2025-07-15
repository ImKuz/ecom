plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.application)
}

group = "io.kuz"
version = "unspecified"

dependencies {
    implementation(project(":common"))
    implementation(project(":infra:proto"))
    implementation(project(":infra:grpc-mapper"))
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
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.data.jdbc)
    implementation(libs.kotlinx.cli)
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("io.kuz.ecom.product.ProductServiceApplicationKt")
}

tasks.register<JavaExec>("runDataGenerator") {
    group = "application"
    description = "Run the data generation"
    mainClass.set("io.kuz.ecom.product.DataGeneratorCLIKt")
    classpath = sourceSets["main"].runtimeClasspath
}
