package com.eliasfs06.spring.restapi.stock.manager.service;

import  com.eliasfs06.spring.restapi.stock.manager.model.Product;
import  com.eliasfs06.spring.restapi.stock.manager.model.ProductConsumption;
import  com.eliasfs06.spring.restapi.stock.manager.model.exceptionsHandler.BusinessException;
import  com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumptionService extends GenericService<ProductConsumption>{

    @Autowired
    private ProductService productService;

    public void cosumeProduct(Product product, Integer quantity) throws BusinessException {
        verifyTotalToConsume(product, quantity);
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productService.create(product);
        ProductConsumption productConsumption = new ProductConsumption(product, quantity);
        this.create(productConsumption);
    }

    public void verifyTotalToConsume(Product product, Integer quantity) throws BusinessException {
        if(product.getStockQuantity() == null || product.getStockQuantity() < quantity){
            throw new BusinessException(MessageCode.CANT_CONSUME_PRODUCT);
        }
    }
}
