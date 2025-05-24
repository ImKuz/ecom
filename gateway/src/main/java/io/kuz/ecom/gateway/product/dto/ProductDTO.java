package io.kuz.ecom.gateway.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {

    private int id;
    private String title;
    private int categoryId;
    private String categoryLabel;
    private List<ProductAttributeDTO> attributes;
    private BigDecimal price;
}
