package io.kuz.ecom.gateway.domain.shop.mapper;

import io.kuz.ecom.gateway.common.dto.PaginationMetaDTO;
import io.kuz.ecom.gateway.common.mapper.PaginationMetaMapper;
import io.kuz.ecom.gateway.domain.shop.dto.*;
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

    public static AddCartItemRequestDTO toDTO(AddCartItemRequest grpcRequest) {
        return AddCartItemRequestDTO.builder()
            .userId(grpcRequest.getUserId())
            .item(toDTO(grpcRequest.getItem()))
            .build();
    }

    public static AddCartItemRequest toGrpc(AddCartItemRequestDTO requestDTO) {
        return AddCartItemRequest.newBuilder()
            .setUserId(requestDTO.getUserId())
            .setItem(toGrpc(requestDTO.getItem()))
            .build();
    }

    public static RemoveCartItemsRequestDTO toDTO(RemoveCartItemsRequest grpcRequest) {
        return RemoveCartItemsRequestDTO.builder()
            .userId(grpcRequest.getUserId())
            .itemIds(grpcRequest.getItemIdsList())
            .build();
    }

    public static RemoveCartItemsRequest toGrpc(RemoveCartItemsRequestDTO requestDTO) {
        return RemoveCartItemsRequest.newBuilder()
            .setUserId(requestDTO.getUserId())
            .addAllItemIds(requestDTO.getItemIds())
            .build();
    }

    public static ChangeCartItemQuantityRequestDTO toDTO(ChangeCartItemQuantityRequest grpcRequest) {
        return ChangeCartItemQuantityRequestDTO.builder()
            .userId(grpcRequest.getUserId())
            .productId(grpcRequest.getProductId())
            .count(grpcRequest.getCount())
            .build();
    }

    public static ChangeCartItemQuantityRequest toGrpc(ChangeCartItemQuantityRequestDTO requestDTO) {
        return ChangeCartItemQuantityRequest.newBuilder()
            .setUserId(requestDTO.getUserId())
            .setProductId(requestDTO.getProductId())
            .setCount(requestDTO.getCount())
            .build();
    }

    public static ProcessCartPurchaseRequestDTO toDTO(ProcessCartPurchaseRequest grpcRequest) {
        return ProcessCartPurchaseRequestDTO.builder()
            .userId(grpcRequest.getUserId())
            .items(grpcRequest.getItemsList().stream()
                .map(CartMapper::toDTO)
                .collect(Collectors.toList()))
            .build();
    }

    public static ProcessCartPurchaseRequest toGrpc(ProcessCartPurchaseRequestDTO requestDTO) {
        return ProcessCartPurchaseRequest.newBuilder()
            .setUserId(requestDTO.getUserId())
            .addAllItems(requestDTO.getItems().stream()
                .map(CartMapper::toGrpc)
                .collect(Collectors.toList()))
            .build();
    }
}