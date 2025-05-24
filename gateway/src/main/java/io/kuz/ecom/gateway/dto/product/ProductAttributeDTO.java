package io.kuz.ecom.gateway.dto.product;

import java.util.List;

public class ProductAttributeDTO {

    private int id;
    private String label;
    private List<ProductAttributeOptionDTO> options;

    public ProductAttributeDTO(int id, String label, List<ProductAttributeOptionDTO> options) {
        this.id = id;
        this.label = label;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ProductAttributeOptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<ProductAttributeOptionDTO> options) {
        this.options = options;
    }
}
