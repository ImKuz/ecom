package io.kuz.ecom.gateway.dto.product;

public class ProductAttributeOptionDTO {

    private int id;
    private int attributeId;
    private String label;

    public ProductAttributeOptionDTO(int id, int attributeId, String label) {
        this.id = id;
        this.attributeId = attributeId;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
