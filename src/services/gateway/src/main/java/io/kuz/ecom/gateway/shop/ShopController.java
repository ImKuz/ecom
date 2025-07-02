package io.kuz.ecom.gateway.shop;

import io.kuz.ecom.gateway.auth.dto.SignUpResendCodeRequestDTO;
import io.kuz.ecom.gateway.common.response.ApiResponse;
import io.kuz.ecom.gateway.product.mapper.ProductMapper;
import io.kuz.ecom.gateway.shop.dto.*;
import io.kuz.ecom.gateway.shop.mapper.CartMapper;
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
    public ApiResponse<GetCartItemsResponseDTO> getCartItems(
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

        return ApiResponse.success(
            CartMapper.toDTO(response)
        );
    }

    @PostMapping("/cart/add")
    public ApiResponse<Void> addCartItem(
        @RequestBody AddCartItemRequestDTO body
    ) {
        AddCartItemRequest request = CartMapper.toGrpc(body);
        shopStub.addCartItem(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/cart/remove")
    public ApiResponse<Void> addCartItem(
        @RequestBody RemoveCartItemsRequestDTO body
    ) {
        RemoveCartItemsRequest request = CartMapper.toGrpc(body);
        shopStub.removeCartItems(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/cart/update-count")
    public ApiResponse<Void> addCartItem(
        @RequestBody ChangeCartItemQuantityRequestDTO body
    ) {
        ChangeCartItemQuantityRequest request = CartMapper.toGrpc(body);
        shopStub.changeCartItemQuantity(request);
        return ApiResponse.success(null);
    }

    @PostMapping("/cart/process")
    public ApiResponse<Void> processCart(
        @RequestBody ProcessCartPurchaseRequestDTO body
    ) {
        ProcessCartPurchaseRequest request = CartMapper.toGrpc(body);
        shopStub.processCartPurchase(request);
        return ApiResponse.success(null);
    }
}
