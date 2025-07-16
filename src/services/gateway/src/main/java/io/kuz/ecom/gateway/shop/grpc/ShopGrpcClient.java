package io.kuz.ecom.gateway.shop.grpc;

import com.google.protobuf.Empty;
import io.kuz.ecom.gateway.shop.dto.*;
import io.kuz.ecom.gateway.shop.mapper.CartMapper;
import io.kuz.ecom.proto.shop.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopGrpcClient {

    private final ShopServiceGrpc.ShopServiceBlockingStub shopStub;

    @Autowired
    public ShopGrpcClient(ShopServiceGrpc.ShopServiceBlockingStub shopStub) {
        this.shopStub = shopStub;
    }

    public GetCartItemsResponseDTO getCartItems(
        int limit,
        int offset,
        String userId
    ) {
        var request = GetCartItemsRequest
            .newBuilder()
            .setUserId(userId)
            .setLimit(limit)
            .setOffset(offset)
            .build();

        var response = shopStub.getCartItems(request);
        return CartMapper.toDTO(response);
    }

    public void addCartItem(
        String userId,
        AddCartItemRequestDTO request
    ) {
        AddCartItemRequest grpcRequest = CartMapper.toGrpc(request, userId);
        Empty empty = shopStub.addCartItem(grpcRequest);
    }

    public void removeCartItems(
        String userId,
        RemoveCartItemsRequestDTO request
    ) {
        RemoveCartItemsRequest grpcRequest = CartMapper.toGrpc(request, userId);
        Empty empty = shopStub.removeCartItems(grpcRequest);
    }

    public void changeCartItemQuantity(
        String userId,
        ChangeCartItemQuantityRequestDTO request
    ) {
        ChangeCartItemQuantityRequest grpcRequest = CartMapper.toGrpc(request, userId);
        Empty empty = shopStub.changeCartItemQuantity(grpcRequest);
    }

    public void processCartPurchase(
        String userId,
        ProcessCartPurchaseRequestDTO request
    ) {
        ProcessCartPurchaseRequest grpcRequest = CartMapper.toGrpc(request, userId);
        Empty empty = shopStub.processCartPurchase(grpcRequest);
    }
}
