plugins {
    kotlin("jvm")
}

group = "io.kuz"
version = "unspecified"

dependencies {
    implementation("com.google.protobuf:protobuf-java:4.28.2")
    implementation(project(":proto"))
    implementation(project(":common"))
}