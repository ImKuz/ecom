package io.kuz.ecom.product.infra.grpc

import com.google.protobuf.Empty
import io.kuz.ecom.product.app.*
import io.kuz.ecom.product.domain.model.ProductAttributeCreateInputModel
import io.kuz.ecom.product.domain.model.ProductAttributeOptionCreateInputModel
import io.kuz.ecom.product.domain.model.ProductCategoryCreateInputModel
import io.kuz.ecom.product.domain.model.ProductCreateInputModel
import io.kuz.ecom.proto.product.DebugProductServiceGrpcKt
import io.kuz.ecom.proto.product.GenerateDataRequest
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class DebugProductGrpcService(
    private val batchCreateProductCase: BatchCreateProduct,
    private val batchCreateProductAttributeCase: BatchCreateProductAttribute,
    private val batchCreateProductAttributeOptionCase: BatchCreateProductAttributeOption,
    private val batchCreateProductCategoryCase: BatchCreateProductCategory,
): DebugProductServiceGrpcKt.DebugProductServiceCoroutineImplBase() {

    private var categoryAttributesMap = mutableMapOf<Long, MutableList<Long>>()
    private var attributeOptionsMap = mutableMapOf<Long, MutableList<Long>>()
    private val lastAttributeId = Box(0)
    private val lastOptionId = Box(0)

    override suspend fun generateData(request: GenerateDataRequest): Empty {
        generateCategories(request.categoriesCount)

        for (categoryId in 1..request.categoriesCount) {
            generateAttributes(
                categoryId,
                request.attributesPerCategoryMin,
                request.attributesPerCategoryMax,
            )

            generateOptions(
                categoryId,
                categoryAttributesMap.getOrPut(categoryId, { mutableListOf() }),
                request.optionsPerAttributeMin,
                request.optionsPerAttributeMax,
            )
        }

        generateProducts(
            request.productsCount,
            request.categoriesCount
        )

        return Empty.newBuilder().build()
    }

    private fun generateProducts(
        productsCount: Long,
        categoriesCount: Long
    ) = runBlocking {
        val inputs = mutableListOf<ProductCreateInputModel>()
        for (id in 1..productsCount) {
            val categoryId = (1..categoriesCount).random()
            val attributeOptions = mutableListOf<String>()
            val attributes = categoryAttributesMap[categoryId] ?: return@runBlocking

            for (attributeId in attributes) {
                val options = attributeOptionsMap[attributeId] ?: return@runBlocking
                for (optionId in options) {
                    if (kotlin.random.Random.nextBoolean()) {
                        attributeOptions.add("$attributeId:$optionId")
                    }
                }
            }

            inputs.add(
                ProductCreateInputModel(
                    title = "Product $id",
                    categoryId = categoryId.toInt(),
                    attributeOptions = attributeOptions,
                    priceCents = (100..1000000).random().toLong()
                )
            )
        }
        batchCreateProductCase(inputs)
    }

    private fun generateAttributes(
        categoryId: Long,
        attributesPerCategoryMin: Long,
        attributesPerCategoryMax: Long
    ) = runBlocking {
        val inputs = mutableListOf<ProductAttributeCreateInputModel>()
        val attributesCount = (attributesPerCategoryMin..attributesPerCategoryMax).random()
        repeat(attributesCount.toInt()) {
            increment(lastAttributeId)
            inputs.add(
                ProductAttributeCreateInputModel(
                    categoryId = categoryId.toInt(),
                    code = "cat$categoryId.att${lastAttributeId.value}"
                )
            )
            categoryAttributesMap
                .getOrPut(categoryId, { mutableListOf() })
                .add(lastAttributeId.value.toLong())
        }
        batchCreateProductAttributeCase(inputs)
    }

    private fun generateOptions(
        categoryId: Long,
        attributes: List<Long>,
        optionsPerAttributeMin: Long,
        optionsPerAttributeMax: Long
    ) = runBlocking {
        val inputs = mutableListOf<ProductAttributeOptionCreateInputModel>()
        for (attributeId in attributes) {
            val optionsCount = (optionsPerAttributeMin..optionsPerAttributeMax).random()
            repeat(optionsCount.toInt()) {
                increment(lastOptionId)
                inputs.add(
                    ProductAttributeOptionCreateInputModel(
                        attributeId = attributeId,
                        code = "cat$categoryId.att$attributeId.opt${lastOptionId.value}"
                    )
                )
                attributeOptionsMap
                    .getOrPut(attributeId, { mutableListOf() })
                    .add(lastOptionId.value.toLong())
            }
        }
        batchCreateProductAttributeOptionCase(inputs)
    }

    private fun generateCategories(count: Long) = runBlocking {
        val inputs = mutableListOf<ProductCategoryCreateInputModel>()
        for (id in 1..count) {
            inputs.add(
                ProductCategoryCreateInputModel(
                    code = "cat$id"
                )
            )
        }
        batchCreateProductCategoryCase(inputs)
    }
}

private data class Box(var value: Int)

private fun increment(box: Box) {
    box.value += 1
}