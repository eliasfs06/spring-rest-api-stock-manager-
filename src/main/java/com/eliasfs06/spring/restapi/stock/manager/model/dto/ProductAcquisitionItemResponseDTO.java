package com.eliasfs06.spring.restapi.stock.manager.model.dto;

import com.eliasfs06.spring.restapi.stock.manager.model.Product;

public class ProductAcquisitionItemResponseDTO {
    private Product product;
    private Integer quantity;

    public ProductAcquisitionItemResponseDTO(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
