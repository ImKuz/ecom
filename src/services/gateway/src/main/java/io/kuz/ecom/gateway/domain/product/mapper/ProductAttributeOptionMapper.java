package io.kuz.ecom.gateway.domain.product.mapper;

import io.kuz.ecom.gateway.domain.product.dto.ProductAttributeOptionDTO;
import io.kuz.ecom.proto.product.ProductAttributeOption;

public class ProductAttributeOptionMapper {

    public static ProductAttributeOptionDTO toDto(ProductAttributeOption proto) {
        return new ProductAttributeOptionDTO(
                (int) proto.getId(),
                (int) proto.getAttributeId(),
                optionLabelForCode(proto.getCode())
        );
    }

    private static String optionLabelForCode(String code) {
        return code;
    }
}
