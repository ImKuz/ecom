package io.kuz.ecom.shop.domain.model;

import io.kuz.ecom.common.models.common.PaginationMetaModel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GetCartItemsPaginatedListModel {

    private List<CartItemModel> items;
    private PaginationMetaModel meta;
}
