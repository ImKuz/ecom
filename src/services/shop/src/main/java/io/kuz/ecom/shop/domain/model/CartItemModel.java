package io.kuz.ecom.shop.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CartItemModel {
    private Long productId;
    private Long quantity;
}
