package io.kuz.ecom.shop.domain;

import io.kuz.ecom.shop.domain.model.CartItemModel;
import io.kuz.ecom.common.models.common.PaginatedListModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CartService {
    public CompletableFuture<PaginatedListModel<CartItemModel>> getCartItems(String userId, Long limit, Long offset);
    public CompletableFuture<Void> addCartItem(String userId, CartItemModel item);
    public CompletableFuture<Void> removeCartItems(String userId, List<Long> items);
    public CompletableFuture<Void> changeCartItemQuantity(String userId, Long quantity, Long productId);
    public CompletableFuture<Void> processCartPurchase(String userId, List<CartItemModel> items);
}
