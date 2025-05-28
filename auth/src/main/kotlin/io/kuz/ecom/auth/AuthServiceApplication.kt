package io.kuz.ecom.product

import io.grpc.ServerBuilder
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class AuthServiceApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            AuthServiceApplication().run()
        }
    }

    fun run() {
        val context = AnnotationConfigApplicationContext(AuthServiceAppConfig::class.java)

//        val service = context.getBean(ProductGrpcService::class.java)
//        val server = ServerBuilder.forPort(7001)
//            .addService(service)
//            .build()
//            .start()
//
//        val debugServer = ServerBuilder.forPort(8889)
//            .addService(debugService)
//            .build()
//            .start()
//
//        Thread {
//            debugServer.awaitTermination()
//        }.start()
//
//        server.awaitTermination()
    }
}