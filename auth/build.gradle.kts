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
    implementation(libs.spring.context)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.postgresql)
    implementation(libs.hikaricp)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
