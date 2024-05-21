package com.eliasfs06.spring.restapi.stock.manager.service;

import  com.eliasfs06.spring.restapi.stock.manager.model.Product;
import  com.eliasfs06.spring.restapi.stock.manager.model.ProductAcquisition;
import  com.eliasfs06.spring.restapi.stock.manager.model.ProductAcquisitionItem;
import  com.eliasfs06.spring.restapi.stock.manager.model.dto.ProductAcquisitionItemDTO;
import  com.eliasfs06.spring.restapi.stock.manager.model.exceptionsHandler.BusinessException;
import  com.eliasfs06.spring.restapi.stock.manager.repository.ProductAcquisitionRepository;
import  com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductAcquisitionService extends GenericService<ProductAcquisition>{

    @Autowired
    private ProductAcquisitionRepository repository;

    @Autowired
    private ProductAcquisitionItemService productAcquisitionItemService;

    @Autowired
    private ProductService productService;

    public Page<ProductAcquisition> getPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProductAcquisition> list;
        List<ProductAcquisition> productAcquisitions = this.findAll();

        productAcquisitions.forEach(productAcquisition -> {
            productAcquisition.setItens(productAcquisitionItemService.findByProductAcquisition(productAcquisition.getId()));
        });

        if (productAcquisitions.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, productAcquisitions.size());
            list = productAcquisitions.subList(startItem, toIndex);
        }

        return new PageImpl<ProductAcquisition>(list, PageRequest.of(currentPage, pageSize), productAcquisitions.size());
    }

    public List<ProductAcquisition> findAll(){
        return repository.findAll();
    }

    public void save(ProductAcquisition productAcquisition, List<ProductAcquisitionItemDTO> productAcquisitionItens) {
        productAcquisition.setAquisitionDate(new Date());
        productAcquisition = repository.save(productAcquisition);
        ProductAcquisition finalProductAcquisition = productAcquisition;

        List<ProductAcquisitionItem> itens = new ArrayList<>();
        productAcquisitionItens.forEach(item -> {
            Product product = productService.get(Long.valueOf(item.getProductId()));
            ProductAcquisitionItem newItem = new ProductAcquisitionItem(product, Integer.valueOf(item.getQuantity()), finalProductAcquisition);
            productAcquisitionItemService.create(newItem);
            itens.add(newItem);
        });
    }
    public void deleteAcquisition(Long id) throws BusinessException {
        ProductAcquisition productAcquisition = get(id);

        verifyQuantityInStockToDelete(productAcquisition);

        for(int i = 0; i < productAcquisition.getItens().size(); i ++){
            ProductAcquisitionItem item = productAcquisition.getItens().get(i);
            productAcquisitionItemService.deleteItem(item.getId());
        }
        repository.deleteById(id);
    }

    private void verifyQuantityInStockToDelete(ProductAcquisition productAcquisition) throws BusinessException {
        Map<Product, Integer> totalPerProduct = new HashMap<>();

        for(int i = 0; i < productAcquisition.getItens().size(); i ++){
            ProductAcquisitionItem item = productAcquisition.getItens().get(i);
            int total = totalPerProduct.getOrDefault(item.getProduct(), 0);
            totalPerProduct.put(item.getProduct(), total + item.getQuantity());
        }

        for (Map.Entry<Product, Integer> entry : totalPerProduct.entrySet()) {
            Integer totalInStock = productService.get(entry.getKey().getId()).getStockQuantity();
            if(totalInStock == null || totalInStock < entry.getValue()){
                throw new BusinessException(MessageCode.CANT_DELETE_ACQUISITION);
            }
        }

    }
}
