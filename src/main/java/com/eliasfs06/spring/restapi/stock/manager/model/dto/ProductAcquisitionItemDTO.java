package com.eliasfs06.spring.restapi.stock.manager.model.dto;

public class ProductAcquisitionItemDTO {
    private Integer productId;
    private Integer quantity;

    public ProductAcquisitionItemDTO(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public ProductAcquisitionItemDTO() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
