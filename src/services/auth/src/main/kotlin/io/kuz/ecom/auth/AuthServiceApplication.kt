package io.kuz.ecom.product

import io.grpc.ServerBuilder
import io.kuz.ecom.auth.config.AuthApplicationProperties
import io.kuz.ecom.auth.infra.grpc.AuthGrpcService
import io.kuz.ecom.auth.infra.grpc.AuthGrpcServiceRequestInterceptor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthServiceApplication

fun main(args: Array<String>) {
    val context = runApplication<AuthServiceApplication>(*args)
    val service = context.getBean(AuthGrpcService::class.java)
    val props = context.getBean(AuthApplicationProperties::class.java)

    val server = ServerBuilder
        .forPort(props.port)
        .addService(service)
        .build()
        .start()

    server.awaitTermination()
}