package io.kuz.ecom.gateway.shop;

import io.kuz.ecom.gateway.auth.dto.ValidateTokenRequestDTO;
import io.kuz.ecom.gateway.auth.grpc.service.AuthGrpcClient;
import io.kuz.ecom.gateway.common.dto.ApiResponseDTO;
import io.kuz.ecom.gateway.shop.dto.*;
import io.kuz.ecom.gateway.shop.grpc.ShopGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ShopController {

    private final ShopGrpcClient shopClient;
    
    private final AuthGrpcClient authClient;

    @Autowired
    public ShopController(ShopGrpcClient client, AuthGrpcClient authClient) {
        this.shopClient = client;
        this.authClient = authClient;
    }

    @GetMapping("/cart/items")
    public ApiResponseDTO<GetCartItemsResponseDTO> getCartItems(
        @RequestHeader HttpHeaders headers,
        @RequestParam(name = "limit") Integer limit,
        @RequestParam(name = "offset") Integer offset
    ) {
        var userId = validateUser(headers.getFirst(HttpHeaders.AUTHORIZATION));
        return ApiResponseDTO.success(
            shopClient.getCartItems(limit, offset, userId)
        );
    }

    @PostMapping("/cart/add")
    public ApiResponseDTO<Void> addCartItem(
        @RequestHeader HttpHeaders headers,
        @RequestBody AddCartItemRequestDTO body
    ) {
        var userId = validateUser(headers.getFirst(HttpHeaders.AUTHORIZATION));
        shopClient.addCartItem(userId, body);
        return ApiResponseDTO.success(null);
    }

    @PostMapping("/cart/remove")
    public ApiResponseDTO<Void> removeCartItems(
        @RequestHeader HttpHeaders headers,
        @RequestBody RemoveCartItemsRequestDTO body
    ) {
        var userId = validateUser(headers.getFirst(HttpHeaders.AUTHORIZATION));
        shopClient.removeCartItems(userId, body);
        return ApiResponseDTO.success(null);
    }

    @PostMapping("/cart/update-count")
    public ApiResponseDTO<Void> changeCartItemQuantity(
        @RequestHeader HttpHeaders headers,
        @RequestBody ChangeCartItemQuantityRequestDTO body
    ) {
        var userId = validateUser(headers.getFirst(HttpHeaders.AUTHORIZATION));
        shopClient.changeCartItemQuantity(userId, body);
        return ApiResponseDTO.success(null);
    }

    @PostMapping("/cart/process")
    public ApiResponseDTO<Void> processCart(
        @RequestHeader HttpHeaders headers,
        @RequestBody ProcessCartPurchaseRequestDTO body
    ) {
        var userId = validateUser(headers.getFirst(HttpHeaders.AUTHORIZATION));
        shopClient.processCartPurchase(userId, body);
        return ApiResponseDTO.success(null);
    }

    private String validateUser(String token) {
        if (token == null) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, 
                "Missing auth token"
            );
        }
        var request = new ValidateTokenRequestDTO(token);
        return authClient.validateToken(request).getUserId();
    }
}
