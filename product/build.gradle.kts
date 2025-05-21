plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring") version "2.1.21"
    application
}

group = "io.kuz"
version = "unspecified"

dependencies {
    implementation(project(":proto"))
    implementation(project(":common"))
    implementation(project(":grpc-mapper"))
    implementation("io.grpc:grpc-kotlin-stub:1.4.1")
    implementation("io.grpc:grpc-netty:1.64.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.exposed:exposed-core:0.50.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.50.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.50.1")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("org.springframework:spring-context:6.1.14")
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("io.kuz.ecom.product.ProductServiceApplicationKt")
}