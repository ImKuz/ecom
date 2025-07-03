package io.kuz.ecom.product

import io.grpc.ServerBuilder
import io.kuz.ecom.product.infra.grpc.DebugProductGrpcService
import io.kuz.ecom.product.infra.grpc.ProductGrpcService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.AnnotationConfigApplicationContext

@SpringBootApplication
class ProductServiceApplication

fun main(args: Array<String>) {
    val context = runApplication<ProductServiceApplication>(*args)
    val service = context.getBean(ProductGrpcService::class.java)
    val debugService = context.getBean(DebugProductGrpcService::class.java)
    val port = context.environment.getProperty("grpc.port")?.toInt() ?: 7001

    val server = ServerBuilder.forPort(port)
        .addService(service)
        .build()
        .start()

    val debugServer = ServerBuilder.forPort(8889)
        .addService(debugService)
        .build()
        .start()

    Thread {
        debugServer.awaitTermination()
    }.start()

    server.awaitTermination()
}