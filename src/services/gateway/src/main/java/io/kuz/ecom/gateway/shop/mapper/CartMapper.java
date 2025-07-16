package io.kuz.ecom.gateway.shop.mapper;

import io.kuz.ecom.gateway.common.dto.PaginationMetaDTO;
import io.kuz.ecom.gateway.common.mapper.PaginationMetaMapper;
import io.kuz.ecom.gateway.domain.shop.dto.*;
import io.kuz.ecom.gateway.shop.dto.*;
import io.kuz.ecom.proto.shop.CartItem;
import io.kuz.ecom.proto.shop.GetCartItemsResponse;
import io.kuz.ecom.proto.shop.AddCartItemRequest;
import io.kuz.ecom.proto.shop.ChangeCartItemQuantityRequest;
import io.kuz.ecom.proto.shop.ProcessCartPurchaseRequest;
import io.kuz.ecom.proto.shop.RemoveCartItemsRequest;

import java.util.stream.Collectors;

public class CartMapper {

    public static CartItemDTO toDTO(CartItem grpcCartItem) {
        return CartItemDTO.builder()
            .productId(grpcCartItem.getProductId())
            .quantity(grpcCartItem.getQuantity())
            .build();
    }

    public static CartItem toGrpc(CartItemDTO cartItemDTO) {
        return CartItem.newBuilder()
            .setProductId(cartItemDTO.getProductId())
            .setQuantity(cartItemDTO.getQuantity())
            .build();
    }

    public static GetCartItemsResponseDTO toDTO(GetCartItemsResponse grpcResponse) {
        PaginationMetaDTO meta = PaginationMetaMapper.toDTO(grpcResponse.getMeta());
        return GetCartItemsResponseDTO.builder()
            .items(
                grpcResponse
                    .getItemsList()
                    .stream()
                    .map(CartMapper::toDTO)
                    .collect(Collectors.toList())
            )
            .meta(meta)
            .build();
    }

    public static AddCartItemRequest toGrpc(
         AddCartItemRequestDTO requestDTO,
         String userId
    ) {
        return AddCartItemRequest.newBuilder()
            .setUserId(userId)
            .setItem(toGrpc(requestDTO.getItem()))
            .build();
    }

    public static RemoveCartItemsRequest toGrpc(
        RemoveCartItemsRequestDTO requestDTO,
        String userId
    ) {
        return RemoveCartItemsRequest.newBuilder()
            .setUserId(userId)
            .addAllItemIds(requestDTO.getItemIds())
            .build();
    }

    public static ChangeCartItemQuantityRequest toGrpc(
        ChangeCartItemQuantityRequestDTO requestDTO,
        String userId
    ) {
        return ChangeCartItemQuantityRequest.newBuilder()
            .setUserId(userId)
            .setProductId(requestDTO.getProductId())
            .setCount(requestDTO.getCount())
            .build();
    }

    public static ProcessCartPurchaseRequest toGrpc(
        ProcessCartPurchaseRequestDTO requestDTO,
        String userId
    ) {
        return ProcessCartPurchaseRequest.newBuilder()
            .setUserId(userId)
            .addAllItems(requestDTO.getItems().stream()
                .map(CartMapper::toGrpc)
                .collect(Collectors.toList()))
            .build();
    }
}