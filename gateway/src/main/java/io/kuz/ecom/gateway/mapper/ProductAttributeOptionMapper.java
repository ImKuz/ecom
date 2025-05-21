package io.kuz.ecom.gateway.mapper;

import io.kuz.ecom.common.dto.ProductAttributeOptionDTO;
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
