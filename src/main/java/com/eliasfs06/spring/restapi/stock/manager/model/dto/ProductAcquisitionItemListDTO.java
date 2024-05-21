package com.eliasfs06.spring.restapi.stock.manager.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductAcquisitionItemListDTO {

    private List<ProductAcquisitionItemDTO> productAcquisitionItens = new ArrayList<>();
    private Long userId;

    public List<ProductAcquisitionItemDTO> getProductAcquisitionItens() {
        return productAcquisitionItens;
    }

    public void setProductAcquisitionItens(List<ProductAcquisitionItemDTO> productAcquisitionItens) {
        this.productAcquisitionItens = productAcquisitionItens;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
