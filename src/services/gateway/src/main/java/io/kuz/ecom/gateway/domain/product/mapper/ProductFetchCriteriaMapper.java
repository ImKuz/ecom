package io.kuz.ecom.gateway.domain.product.mapper;

import io.kuz.ecom.gateway.common.exception.InputException;
import io.kuz.ecom.proto.product.ProductFetchCriteria;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class ProductFetchCriteriaMapper {

    public static ProductFetchCriteria mapFromQuery(
        String query,
        Long categoryId,
        String attributeOptions,
        Long minPriceCents,
        Long maxPriceCents
    ) throws InputException {
        ProductFetchCriteria.Builder criteria = ProductFetchCriteria.newBuilder();

        if (query != null) {
            criteria.setQuery(query);
        }

        if (categoryId != null) {
            criteria.setCategoryId(categoryId);
        }

        if (attributeOptions != null) {
            String[] list = attributeOptions.split(",");
            if (list.length == 0) {
                throw new InputException("attribute_options");
            }
            criteria.addAllAttributeOptions(Arrays.asList(list));
        }

        if (minPriceCents != null) {
            criteria.setMinPriceCents(minPriceCents);
        }

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
