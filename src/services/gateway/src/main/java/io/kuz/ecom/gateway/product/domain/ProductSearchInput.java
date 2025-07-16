package io.kuz.ecom.gateway.product.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSearchInput {
    private String query;
    private Long categoryId;
    private String attributeOptions;
    private Long minPriceCents;
    private Long maxPriceCents;
    private Integer page;
    private Integer pageSize;
}
