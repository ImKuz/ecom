package io.kuz.ecom.gateway.dto.product;

import io.kuz.ecom.gateway.dto.PaginationMetaDTO;
import java.util.List;

public class ProductListDTO {

    private List<ProductDTO> products;
    private PaginationMetaDTO meta;

    public ProductListDTO(List<ProductDTO> products, PaginationMetaDTO meta) {
        this.products = products;
        this.meta = meta;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public PaginationMetaDTO getMeta() {
        return meta;
    }

    public void setMeta(PaginationMetaDTO meta) {
        this.meta = meta;
    }
}
