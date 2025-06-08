package io.kuz.ecom.auth.infra.grpc

import io.grpc.ForwardingServerCallListener
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import io.grpc.Metadata

class AuthGrpcServiceRequestInterceptor: ServerInterceptor {
    private val logger = org.slf4j.LoggerFactory.getLogger(AuthGrpcServiceRequestInterceptor::class.java)

    override fun <ReqT, RespT> interceptCall(
        call: ServerCall<ReqT, RespT>,
        headers: Metadata,
        next: ServerCallHandler<ReqT, RespT>
    ): ServerCall.Listener<ReqT> {
        logger.info("Interceptor is active for method: {}", call.methodDescriptor.fullMethodName)
        val listener = next.startCall(call, headers)
        return object : ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(listener) {
            override fun onHalfClose() {
                try {
                    super.onHalfClose()
                } catch (ex: Exception) {
                    logger.error("gRPC call exception: {}", ex.message, ex)
                    throw ex
                }
            }
        }
    }
}