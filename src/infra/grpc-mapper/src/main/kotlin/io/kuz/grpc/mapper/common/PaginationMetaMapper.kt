package io.kuz.grpc.mapper.common

import io.kuz.ecom.common.models.common.PaginationMetaModel
import io.kuz.ecom.proto.common.PaginationMeta

object PaginationMetaMapper {

    fun toGRPC(model: PaginationMetaModel): PaginationMeta {
        return PaginationMeta
            .newBuilder()
            .setPage(model.page.toInt())
            .setPageSize(model.pageSize.toInt())
            .setPageCount(model.pageCount.toInt())
            .build()
    }

    fun toModel(grpc: PaginationMeta): PaginationMetaModel {
        return  PaginationMetaModel(
            page = grpc.page.toLong(),
            pageSize = grpc.pageSize.toLong(),
            pageCount = grpc.pageCount.toLong(),
        )
    }
}
