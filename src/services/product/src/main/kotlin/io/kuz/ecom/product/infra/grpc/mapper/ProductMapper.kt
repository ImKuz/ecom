package io.kuz.ecom.product.infra.grpc.mapper

import io.kuz.ecom.common.models.product.ProductAttributeModel
import io.kuz.ecom.common.models.product.ProductAttributeOptionModel
import io.kuz.ecom.common.models.product.ProductModel
import io.kuz.ecom.product.domain.model.ProductAttributeCreateInputModel
import io.kuz.ecom.product.domain.model.ProductAttributeOptionCreateInputModel
import io.kuz.ecom.product.domain.model.ProductCategoryCreateInputModel
import io.kuz.ecom.product.domain.model.ProductCreateInputModel
import io.kuz.ecom.proto.product.*

object ProductMapper {

    fun toGRPC(model: ProductModel): Product {
        val builder: Product.Builder = Product.newBuilder()

        builder
            .setId(model.id.toLong())
            .setTitle(model.title)
            .setCategoryId(model.categoryId.toLong())
            .setCategoryCode(model.categoryCode)
            .setPriceCents(model.priceCents)

        for (attributeModel in model.attributes) {
            builder.addAttributes(
                ProductAttributeMapper.toGRPC(attributeModel)
            )
        }

        return builder.build()
    }

    fun toModel(grpc: ProductCreateInput): ProductCreateInputModel {
        return ProductCreateInputModel(
            title = grpc.title,
            categoryId = grpc.categoryId,
            attributeOptions = grpc.attributeOptionsList,
            priceCents = grpc.priceCents
        )
    }
}

object ProductAttributeMapper {

    fun toGRPC(model: ProductAttributeModel): ProductAttribute {
        val builder: ProductAttribute.Builder = ProductAttribute.newBuilder()

        builder
            .setId(model.id.toLong())
            .setCode(model.code)

        for (optionModel in model.options) {
            builder.addOptions(
                ProductAttributeOptionMapper.toGRPC(optionModel)
            )
        }

        return builder.build()
    }

    fun toModel(grpc: ProductAttributeCreateInput): ProductAttributeCreateInputModel {
        return  ProductAttributeCreateInputModel(
            categoryId = grpc.categoryId,
            code = grpc.code,
        )
    }
}

object ProductAttributeOptionMapper {

    fun toGRPC(model: ProductAttributeOptionModel): ProductAttributeOption {
        return ProductAttributeOption
            .newBuilder()
            .setId(model.id.toLong())
            .setAttributeId(model.attributeId.toLong())
            .setCode(model.code)
            .build()
    }

    fun toModel(grpc: ProductAttributeOptionCreateInput): ProductAttributeOptionCreateInputModel {
        return  ProductAttributeOptionCreateInputModel(
            attributeId = grpc.attributeId,
            code = grpc.code,
        )
    }
}

object ProductCategoryMapper {

    fun toModel(grpc: ProductCategoryCreateInput): ProductCategoryCreateInputModel {
        return  ProductCategoryCreateInputModel(
            code = grpc.code,
        )
    }
}