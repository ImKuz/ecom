package io.kuz.ecom.gateway.domain.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCartItemsRequestDTO {
    private String userId;
    private Long page;
    private Long pageSize;
}
