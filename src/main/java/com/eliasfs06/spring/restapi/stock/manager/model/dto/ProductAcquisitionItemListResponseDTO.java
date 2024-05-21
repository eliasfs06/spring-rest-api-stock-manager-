package com.eliasfs06.spring.restapi.stock.manager.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductAcquisitionItemListResponseDTO {

    private List<ProductAcquisitionItemResponseDTO> productAcquisitionItens = new ArrayList<>();
    private Long userId;

    public List<ProductAcquisitionItemResponseDTO> getProductAcquisitionItens() {
        return productAcquisitionItens;
    }

    public void setProductAcquisitionItens(List<ProductAcquisitionItemResponseDTO> productAcquisitionItens) {
        this.productAcquisitionItens = productAcquisitionItens;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
