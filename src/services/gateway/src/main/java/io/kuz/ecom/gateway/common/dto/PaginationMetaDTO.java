package io.kuz.ecom.gateway.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationMetaDTO {

    private Long total;
    private Long limit;
    private Long offset;
}
