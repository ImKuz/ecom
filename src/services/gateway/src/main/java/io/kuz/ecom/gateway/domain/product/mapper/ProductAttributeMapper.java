package io.kuz.ecom.gateway.domain.product.mapper;

import io.kuz.ecom.gateway.domain.product.dto.ProductAttributeDTO;
import io.kuz.ecom.proto.product.ProductAttribute;
import java.util.stream.Collectors;

public class ProductAttributeMapper {

    public static ProductAttributeDTO toDto(ProductAttribute proto) {
        return new ProductAttributeDTO(
                (int) proto.getId(),
                attributeLabelForCode(proto.getCode()),
                proto.getOptionsList()
                        .stream()
                        .map(ProductAttributeOptionMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    private static String attributeLabelForCode(String code) {
        return code;
    }
}
