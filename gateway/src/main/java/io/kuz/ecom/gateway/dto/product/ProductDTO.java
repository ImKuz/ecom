package io.kuz.ecom.gateway.dto.product;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {

    private int id;
    private String title;
    private int categoryId;
    private String categoryLabel;
    private List<ProductAttributeDTO> attributes;
    private BigDecimal price;

    public ProductDTO(
        int id,
        String title,
        int categoryId,
        String categoryLabel,
        List<ProductAttributeDTO> attributes,
        BigDecimal price
    ) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.categoryLabel = categoryLabel;
        this.attributes = attributes;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    public List<ProductAttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttributeDTO> attributes) {
        this.attributes = attributes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
