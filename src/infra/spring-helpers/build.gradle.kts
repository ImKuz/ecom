plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
}

group = "io.kuz"
version = "unspecified"

dependencies {
    implementation(libs.spring.context)
}