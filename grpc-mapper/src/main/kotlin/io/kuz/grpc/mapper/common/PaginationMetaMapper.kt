package io.kuz.grpc.mapper.common

import io.kuz.ecom.common.models.common.PaginationMetaModel
import io.kuz.ecom.proto.common.PaginationMeta

object PaginationMetaMapper {

    fun toGRPC(model: PaginationMetaModel): PaginationMeta {
        return PaginationMeta
            .newBuilder()
            .setOffset(model.offset.toInt())
            .setLimit(model.limit.toInt())
            .setTotal(model.total.toInt())
            .build()
    }

    fun toModel(grpc: PaginationMeta): PaginationMetaModel {
        return  PaginationMetaModel(
            limit = grpc.limit.toLong(),
            offset = grpc.offset.toLong(),
            total = grpc.total.toLong(),
        )
    }
}
