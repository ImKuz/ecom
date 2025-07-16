package io.kuz.ecom.gateway.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCartItemQuantityRequestDTO {
    private Long productId;
    private Long count;
}
