package io.kuz.ecom.gateway.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductAttributeDTO {

    private int id;
    private String label;
    private List<ProductAttributeOptionDTO> options;
}
