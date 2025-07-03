package io.kuz.ecom.gateway.domain.shop;

import io.kuz.ecom.gateway.common.dto.ApiResponseDTO;
import io.kuz.ecom.gateway.domain.shop.dto.*;
import io.kuz.ecom.gateway.domain.shop.mapper.CartMapper;
import io.kuz.ecom.proto.shop.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShopController {

    private final ShopServiceGrpc.ShopServiceBlockingStub shopStub;

    @Autowired
    public ShopController(ShopServiceGrpc.ShopServiceBlockingStub shopStub) {
        this.shopStub = shopStub;
    }

    @GetMapping("/cart/items")
    public ApiResponseDTO<GetCartItemsResponseDTO> getCartItems(
        @RequestParam(name = "user_id") String userId,
        @RequestParam(name = "limit") Integer limit,
        @RequestParam(name = "offset") Integer offset
    ) {
        GetCartItemsRequest request = GetCartItemsRequest
            .newBuilder()
            .setUserId(userId)
            .setLimit(limit)
            .setOffset(offset)
            .build();

        GetCartItemsResponse response = shopStub.getCartItems(request);

        return ApiResponseDTO.success(
            CartMapper.toDTO(response)
        );
    }

    @PostMapping("/cart/add")
    public ApiResponseDTO<Void> addCartItem(
        @RequestBody AddCartItemRequestDTO body
    ) {
        AddCartItemRequest request = CartMapper.toGrpc(body);
        shopStub.addCartItem(request);
        return ApiResponseDTO.success(null);
    }

    @PostMapping("/cart/remove")
    public ApiResponseDTO<Void> addCartItem(
        @RequestBody RemoveCartItemsRequestDTO body
    ) {
        RemoveCartItemsRequest request = CartMapper.toGrpc(body);
        shopStub.removeCartItems(request);
        return ApiResponseDTO.success(null);
    }

    @PostMapping("/cart/update-count")
    public ApiResponseDTO<Void> addCartItem(
        @RequestBody ChangeCartItemQuantityRequestDTO body
    ) {
        ChangeCartItemQuantityRequest request = CartMapper.toGrpc(body);
        shopStub.changeCartItemQuantity(request);
        return ApiResponseDTO.success(null);
    }

    @PostMapping("/cart/process")
    public ApiResponseDTO<Void> processCart(
        @RequestBody ProcessCartPurchaseRequestDTO body
    ) {
        ProcessCartPurchaseRequest request = CartMapper.toGrpc(body);
        shopStub.processCartPurchase(request);
        return ApiResponseDTO.success(null);
    }
}
