package io.kuz.ecom.product

import io.grpc.ServerBuilder
import io.kuz.ecom.product.infra.grpc.DebugProductGrpcService
import io.kuz.ecom.product.infra.grpc.ProductGrpcService
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class ProductServiceApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            ProductServiceApplication().run()
        }
    }

    fun run() {
        val context = AnnotationConfigApplicationContext(ProductServiceAppConfig::class.java)
        val service = context.getBean(ProductGrpcService::class.java)
        val debugService = context.getBean(DebugProductGrpcService::class.java)

        val server = ServerBuilder.forPort(7001)
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
}