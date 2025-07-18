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
    implementation(project(":infra:spring-helpers"))
    implementation(libs.spring.context)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.data.redis.reactive)
    implementation(libs.grpc.kotlin.stub)
    implementation(libs.grpc.netty)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.postgresql)
    implementation(libs.hikaricp)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.datatype.jsr310)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.java.jwt)
    implementation(libs.bcrypt)
    implementation(libs.micrometer.registry.prometheus)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
