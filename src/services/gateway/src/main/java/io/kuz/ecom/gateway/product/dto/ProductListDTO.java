package io.kuz.ecom.gateway.product.dto;

import io.kuz.ecom.gateway.common.dto.PaginationMetaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductListDTO {

    private List<ProductDTO> products;
    private PaginationMetaDTO meta;
}
