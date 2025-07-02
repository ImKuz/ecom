package io.kuz.ecom.shop.domain.repository;

import io.kuz.ecom.shop.domain.model.CartItemModel;
import io.kuz.ecom.common.models.common.PaginatedListModel;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface CartItemsRepository {
    public CompletableFuture<PaginatedListModel<CartItemModel>> fetchItemList(String userId, Long limit, Long offset);
    public CompletableFuture<Optional<CartItemModel>> fetchItem(String userId, Long productId);
    public CompletableFuture<Void> createItem(String userId, CartItemModel model);
    public CompletableFuture<Void> removeItem(String userId, List<Long> productIds);
    public CompletableFuture<Void> increaseQuantity(String userId, Long productId, Long count);
}
