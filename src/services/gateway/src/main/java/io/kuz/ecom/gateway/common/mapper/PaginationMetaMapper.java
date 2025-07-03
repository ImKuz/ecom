package io.kuz.ecom.gateway.common.mapper;

import io.kuz.ecom.gateway.common.dto.PaginationMetaDTO;
import io.kuz.ecom.proto.common.PaginationMeta;

public class PaginationMetaMapper {

    public static PaginationMetaDTO toDTO(PaginationMeta proto) {
        return new PaginationMetaDTO(
            (long) proto.getPage(),
            (long) proto.getPageSize(),
            (long) proto.getPageCount()
        );
    }
}
