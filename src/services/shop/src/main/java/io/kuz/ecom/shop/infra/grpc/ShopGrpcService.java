package io.kuz.ecom.shop.infra.grpc;

import com.google.protobuf.Empty;
import io.kuz.ecom.shop.domain.CartService;
import io.kuz.ecom.shop.domain.model.CartItemModel;
import io.kuz.ecom.shop.infra.grpc.mapper.CartItemMapper;
import io.grpc.stub.StreamObserver;
import io.kuz.ecom.proto.shop.*;
import io.kuz.grpc.mapper.common.PaginationMetaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopGrpcService extends ShopServiceGrpc.ShopServiceImplBase {

    private final CartService service;

    @Autowired
    ShopGrpcService(CartService service) {
        this.service = service;
    }

    @Override
    public void getCartItems(GetCartItemsRequest request, StreamObserver<GetCartItemsResponse> responseObserver) {
        service
            .getCartItems(request.getUserId(), request.getLimit(), request.getOffset())
            .thenAccept(paginatedList -> {
                List<CartItem> grpcList = paginatedList
                    .getList()
                    .stream()
                    .map(CartItemMapper::toGrpc)
                    .toList();

                GetCartItemsResponse response = GetCartItemsResponse
                    .newBuilder()
                    .setMeta(PaginationMetaMapper.INSTANCE.toGRPC(paginatedList.getMeta()))
                    .addAllItems(grpcList)
                    .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            })
            .exceptionally(ex -> {
                responseObserver.onError(ex);
                return null;
            });
    }

    @Override
    public void addCartItem(AddCartItemRequest request, StreamObserver<Empty> responseObserver) {
        CartItemModel cartItem = CartItemMapper.toModel(request.getItem());
        service
            .addCartItem(request.getUserId(), cartItem)
            .thenAccept(v -> {
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
            })
            .exceptionally(ex -> {
                responseObserver.onError(ex);
                return null;
            });
    }

    @Override
    public void removeCartItems(RemoveCartItemsRequest request, StreamObserver<Empty> responseObserver) {
        service
            .removeCartItems(request.getUserId(), request.getItemIdsList())
            .thenAccept(v -> {
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
            })
            .exceptionally(ex -> {
                responseObserver.onError(ex);
                return null;
            });
    }

    @Override
    public void changeCartItemQuantity(ChangeCartItemQuantityRequest request, StreamObserver<Empty> responseObserver) {
        service
            .changeCartItemQuantity(request.getUserId(), request.getCount(), request.getProductId())
            .thenAccept(v -> {
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
            })
            .exceptionally(ex -> {
                responseObserver.onError(ex);
                return null;
            });
    }

    @Override
    public void processCartPurchase(ProcessCartPurchaseRequest request, StreamObserver<Empty> responseObserver) {
        List<CartItemModel> modelList = request
            .getItemsList()
            .stream()
            .map(CartItemMapper::toModel)
            .toList();

        service
            .processCartPurchase(request.getUserId(), modelList)
            .thenAccept(v -> {
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
            })
            .exceptionally(ex -> {
                responseObserver.onError(ex);
                return null;
            });
    }
}
