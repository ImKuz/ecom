package io.kuz.ecom.product.infra.grpc

import com.google.protobuf.Empty
import io.grpc.Status
import io.kuz.ecom.product.app.*
import io.kuz.ecom.product.infra.grpc.mapper.*
import io.kuz.ecom.proto.product.*
import io.kuz.grpc.mapper.common.PaginationMetaMapper
import org.springframework.stereotype.Component

@Component
class ProductGrpcService(
    private val getProductByIdCase: GetProductById,
    private val getProductListByCriteriaCase: GetProductListByCriteria,
    private val batchCreateProduct: BatchCreateProduct,
    private val batchCreateProductAttributeCase: BatchCreateProductAttribute,
    private val batchCreateProductAttributeOptionCase: BatchCreateProductAttributeOption,
    private val batchCreateProductCategoryCase: BatchCreateProductCategory,
): ProductServiceGrpcKt.ProductServiceCoroutineImplBase() {

    override suspend fun getProductById(request: GetProductByIdRequest): GetProductByIdResponse {
        val product = getProductByIdCase(request.id.toInt())
            ?: throw Status.NOT_FOUND
                .withDescription("Product with id ${request.id} not found")
                .asRuntimeException()

        return GetProductByIdResponse
            .newBuilder()
            .setProduct(ProductMapper.toGRPC(product))
            .build()
    }

    override suspend fun getProductListByCriteria(
        request: GetProductListByCriteriaRequest
    ): GetProductListByCriteriaResponse {
        val result = getProductListByCriteriaCase(
            criteria = ProductFetchCriteriaMapper.toModel(request.criteria),
            offset = request.offset,
            limit = request.limit,
        )

        return GetProductListByCriteriaResponse
            .newBuilder()
            .addAllProducts(result.products.map { ProductMapper.toGRPC(it) })
            .setMeta(PaginationMetaMapper.toGRPC(result.meta))
            .build()
    }

    override suspend fun batchCreateProducts(request: BatchCreateProductRequest): Empty {
        batchCreateProduct(request.productsList.map { ProductMapper.toModel(it) })
        return Empty.newBuilder().build()
    }

    override suspend fun batchCreateProductAttribute(request: BatchCreateProductAttributeRequest): Empty {
        batchCreateProductAttributeCase(
            request.attributesList
                .toList()
                .map { ProductAttributeMapper.toModel(it) }
        )
        return Empty.newBuilder().build()
    }

    override suspend fun batchCreateProductAttributeOption(request: BatchCreateProductAttributeOptionRequest): Empty {
        batchCreateProductAttributeOptionCase(
            request.optionsList
                .toList()
                .map { ProductAttributeOptionMapper.toModel(it) }
        )
        return Empty.newBuilder().build()
    }

    override suspend fun batchCreateCategory(request: BatchCreateCategoryRequest): Empty {
        batchCreateProductCategoryCase(
            request.categoriesList
                .toList()
                .map { ProductCategoryMapper.toModel(it) }
        )
        return Empty.newBuilder().build()
    }
}
