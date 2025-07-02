package io.kuz.ecom.shop.infra.repository;

import io.kuz.ecom.shop.domain.model.CartItemModel;
import io.kuz.ecom.shop.domain.repository.CartItemsRepository;
import io.kuz.ecom.shop.infra.db.entity.CartItemEntity;
import io.kuz.ecom.common.models.common.PaginatedListModel;
import io.kuz.ecom.common.models.common.PaginationMetaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public class CartItemsRepositoryImpl implements CartItemsRepository {

    private final JPACartItemsRepository jpa;

    @Autowired
    CartItemsRepositoryImpl(JPACartItemsRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    @Async
    public CompletableFuture<PaginatedListModel<CartItemModel>> fetchItemList(
        String userId,
        Long limit,
        Long offset
    ) {
        int page = (int)(offset / limit);
        int size = limit.intValue();
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<CartItemEntity> pageResult = jpa.findByUserId(userId, pageRequest);

        List<CartItemModel> items = pageResult
            .getContent()
            .stream()
            .map(this::mapToDomain)
            .toList();

        PaginationMetaModel meta = new PaginationMetaModel(
            limit,
            offset,
            pageResult.getTotalElements()
        );

        PaginatedListModel<CartItemModel> paginatedList = new PaginatedListModel<>(items, meta);
        return CompletableFuture.completedFuture(paginatedList);
    }

    @Override
    @Async
    public CompletableFuture<Optional<CartItemModel>> fetchItem(String userId, Long productId) {
        Optional<CartItemModel> item = jpa
            .findByUserIdAndProductId(userId, productId)
            .map(this::mapToDomain);

        return CompletableFuture.completedFuture(item);
    }

    @Override
    @Async
    public CompletableFuture<Void> createItem(String userId, CartItemModel model) {
        jpa.save(mapToEntity(userId, model));
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public CompletableFuture<Void> removeItem(String userId, List<Long> productIds) {
        jpa.removeByUserIdAndProductIdIn(userId, productIds);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public CompletableFuture<Void> increaseQuantity(String userId, Long productId, Long count) {
        jpa.findByUserIdAndProductId(userId, productId).ifPresent(entity -> {
            Long current = entity.getQuantity();
            entity.setQuantity(current + count);
            jpa.save(entity);
        });

        return CompletableFuture.completedFuture(null);
    }

    private CartItemModel mapToDomain(CartItemEntity entity) {
        return CartItemModel
            .builder()
            .productId(entity.getProductId())
            .quantity(entity.getQuantity())
            .build();
    }

    private CartItemEntity mapToEntity(String userId, CartItemModel model) {
        return CartItemEntity
            .builder()
            .userId(userId)
            .productId(model.getProductId())
            .quantity(model.getQuantity())
            .build();
    }
}

interface JPACartItemsRepository extends JpaRepository<CartItemEntity, Long> {
    Page<CartItemEntity> findByUserId(String userId, Pageable pageable);
    Optional<CartItemEntity> findByUserIdAndProductId(String userId, Long productId);
    void removeByUserIdAndProductIdIn(String userId, List<Long> productIds);
}