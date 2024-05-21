package com.eliasfs06.spring.restapi.stock.manager.service;

import  com.eliasfs06.spring.restapi.stock.manager.model.Product;
import  com.eliasfs06.spring.restapi.stock.manager.model.ProductAcquisitionItem;
import  com.eliasfs06.spring.restapi.stock.manager.repository.ProductAcquisitionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAcquisitionItemService extends GenericService<ProductAcquisitionItem> {

    @Autowired
    private ProductAcquisitionItemRepository repository;

    @Autowired
    private ProductService productService;

    public List<ProductAcquisitionItem> findByProductAcquisition(Long id) {
        return repository.findByProductAcquisition(id);
    }

    public ProductAcquisitionItem save(ProductAcquisitionItem productAcquisitionItem) {
        Product product = productAcquisitionItem.getProduct();
        product.setStockQuantity(product.getStockQuantity() == null ? productAcquisitionItem.getQuantity() : product.getStockQuantity() + productAcquisitionItem.getQuantity());
        productService.create(product);
        return repository.save(productAcquisitionItem);
    }

    public void deleteItem(Long id) {
        ProductAcquisitionItem productAcquisitionItem = get(id);
        Product product = productAcquisitionItem.getProduct();
        product.setStockQuantity(product.getStockQuantity() - productAcquisitionItem.getQuantity());
        productService.create(product);
        repository.save(productAcquisitionItem);
    }

}