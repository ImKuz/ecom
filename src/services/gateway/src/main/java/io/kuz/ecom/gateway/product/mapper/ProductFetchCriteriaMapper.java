package io.kuz.ecom.gateway.product.mapper;

import io.kuz.ecom.gateway.common.exception.InputException;
import io.kuz.ecom.gateway.product.domain.ProductSearchInput;
import io.kuz.ecom.proto.product.ProductFetchCriteria;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class ProductFetchCriteriaMapper {

    public static ProductFetchCriteria mapFromInput(ProductSearchInput input) throws InputException {
        ProductFetchCriteria.Builder criteria = ProductFetchCriteria.newBuilder();

        var query = input.getQuery();

        if (query != null) {
            criteria.setQuery(query);
        }

        var categoryId = input.getCategoryId();

        if (categoryId != null) {
            criteria.setCategoryId(categoryId);
        }

        var attributeOptions = input.getAttributeOptions();

        if (attributeOptions != null) {
            String[] list = attributeOptions.split(",");
            if (list.length == 0) {
                throw new InputException("attribute_options");
            }
            criteria.addAllAttributeOptions(Arrays.asList(list));
        }

        var minPriceCents = input.getMinPriceCents();

        if (minPriceCents != null) {
            criteria.setMinPriceCents(minPriceCents);
        }

        var maxPriceCents = input.getMaxPriceCents();

        if (maxPriceCents != null) {
            criteria.setMaxPriceCents(maxPriceCents);
        }

        boolean isEmpty = Stream
                .of(query, categoryId, minPriceCents, maxPriceCents, attributeOptions)
                .allMatch(Objects::isNull);

        if (isEmpty) {
            throw new InputException("Invalid criteria for product search");
        }

        return criteria.build();
    }
}
