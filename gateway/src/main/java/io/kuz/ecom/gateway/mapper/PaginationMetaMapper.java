package io.kuz.ecom.gateway.mapper;

import io.kuz.ecom.gateway.dto.PaginationMetaDTO;
import io.kuz.ecom.proto.common.PaginationMeta;

public class PaginationMetaMapper {

    public static PaginationMetaDTO toDTO(PaginationMeta proto) {
        return new PaginationMetaDTO(
            (long) proto.getTotal(),
            (long) proto.getLimit(),
            (long) proto.getOffset()
        );
    }
}
