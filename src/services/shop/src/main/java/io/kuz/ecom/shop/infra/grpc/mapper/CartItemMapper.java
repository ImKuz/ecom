package io.kuz.ecom.shop.infra.grpc.mapper;

import io.kuz.ecom.shop.domain.model.CartItemModel;
import io.kuz.ecom.proto.shop.CartItem;

public class CartItemMapper {

    public static CartItem toGrpc(CartItemModel model) {
        return CartItem
            .newBuilder()
            .setProductId(model.getProductId())
            .setQuantity(model.getQuantity())
            .build();
    }

    public static CartItemModel toModel(CartItem grpc) {
        return CartItemModel
            .builder()
            .productId(grpc.getProductId())
            .quantity(grpc.getQuantity())
            .build();
    }
}
