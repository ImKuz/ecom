plugins {
    alias(libs.plugins.java)
    alias(libs.plugins.lombok)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "io.kuz"
version = "unspecified"

dependencies {
    implementation(project(":common"))
    implementation(project(":infra:proto"))
    implementation(project(":infra:grpc-mapper"))
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.postgresql)
    implementation(libs.grpc.netty)
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.stub)
}

tasks.withType<Test> {
    useJUnitPlatform()
}