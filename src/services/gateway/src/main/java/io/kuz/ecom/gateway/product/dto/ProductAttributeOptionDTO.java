package io.kuz.ecom.gateway.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductAttributeOptionDTO {

    private int id;
    private int attributeId;
    private String label;
}
