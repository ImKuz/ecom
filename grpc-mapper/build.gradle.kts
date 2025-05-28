plugins {
    kotlin("jvm")
}

group = "io.kuz"
version = "unspecified"

dependencies {
    implementation(libs.protobuf.java)
    implementation(project(":proto"))
    implementation(project(":common"))
}