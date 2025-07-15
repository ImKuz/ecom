package io.kuz.ecom.product.infra.grpc

import com.google.protobuf.Empty
import io.kuz.ecom.product.app.*
import io.kuz.ecom.product.domain.debug.TestDataGenerator
import io.kuz.ecom.proto.product.DebugProductServiceGrpcKt
import io.kuz.ecom.proto.product.GenerateDataRequest
import org.springframework.stereotype.Component

@Component
class DebugProductGrpcService(
    private val generator: TestDataGenerator
): DebugProductServiceGrpcKt.DebugProductServiceCoroutineImplBase() {

    override suspend fun generateData(request: GenerateDataRequest): Empty {
        generator.generate(
            request.categoriesCount,
            request.productsCount,
            request.attributesCount,
            request.optionsCount,
        )
        return Empty.newBuilder().build()
    }
}
