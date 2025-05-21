package io.kuz.ecom.gateway.mapper;

import io.kuz.ecom.proto.product.Product;
import io.kuz.ecom.common.dto.ProductDTO;
import io.kuz.ecom.common.price.PriceMapper;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDTO toDto(Product proto) {
        return new ProductDTO(
                (int) proto.getId(),
                proto.getTitle(),
                (int) proto.getCategoryId(),
                categoryLabelForCode(proto.getCategoryCode()),
                proto.getAttributesList()
                            .stream()
                            .map(v -> ProductAttributeMapper.toDto(v))
                            .collect(Collectors.toList()),
                BigDecimal.valueOf(proto.getPriceCents(), 2)
        );
    }

    private static String categoryLabelForCode(String code) {
        return code;
    }
}