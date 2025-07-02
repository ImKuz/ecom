package io.kuz.ecom.shop.app;

import io.kuz.ecom.shop.domain.CartService;
import io.kuz.ecom.shop.domain.model.CartItemModel;
import io.kuz.ecom.shop.domain.repository.CartItemsRepository;
import io.kuz.ecom.common.models.common.PaginatedListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemsRepository repo;

    @Autowired
    public CartServiceImpl(CartItemsRepository repo) {
        this.repo = repo;
    }

    @Override
    @Async
    public CompletableFuture<PaginatedListModel<CartItemModel>> getCartItems(
        String userId,
        Long limit,
        Long offset
    ) {
        return repo.fetchItemList(userId, limit, offset);
    }

    @Override
    @Async
    public CompletableFuture<Void> addCartItem(String userId, CartItemModel item) {
        return repo.createItem(userId, item);
    }

    @Override
    @Async
    public CompletableFuture<Void> removeCartItems(String userId, List<Long> items) {
        return repo.removeItem(userId, items);
    }

    @Override
    @Async
    public CompletableFuture<Void> changeCartItemQuantity(
        String userId,
        Long quantity,
        Long productId
    ) {
        return repo.increaseQuantity(userId, productId, quantity);
    }

    @Override
    @Async
    public CompletableFuture<Void> processCartPurchase(String userId, List<CartItemModel> items) {
        List<Long> ids = items
            .stream()
            .map(CartItemModel::getProductId)
            .toList();
        return removeCartItems(userId, ids);
    }
}
