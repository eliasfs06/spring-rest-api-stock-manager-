package com.eliasfs06.spring.restapi.stock.manager.service;

import  com.eliasfs06.spring.restapi.stock.manager.model.Product;
import  com.eliasfs06.spring.restapi.stock.manager.model.ProductConsumptionRequest;
import  com.eliasfs06.spring.restapi.stock.manager.model.enums.RequestStatus;
import  com.eliasfs06.spring.restapi.stock.manager.model.exceptionsHandler.BusinessException;
import  com.eliasfs06.spring.restapi.stock.manager.repository.ProductConsumptionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductConsumptionRequestService extends GenericService<ProductConsumptionRequest>{

    @Autowired
    private ProductConsumptionService productConsumptionService;

    @Autowired
    private ProductConsumptionRequestRepository repository;

    public ProductConsumptionRequest requestToCosumeProduct(Product product, Integer quantity, String description) throws BusinessException {
        productConsumptionService.verifyTotalToConsume(product, quantity);
        ProductConsumptionRequest request = new ProductConsumptionRequest(product, quantity, description);
        return repository.save(request);
    }

    public Page<ProductConsumptionRequest> getPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProductConsumptionRequest> list;
        List<ProductConsumptionRequest> requests = repository.findAll();

        if (requests.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, requests.size());
            list = requests.subList(startItem, toIndex);
        }

        return new PageImpl<ProductConsumptionRequest>(list, PageRequest.of(currentPage, pageSize), requests.size());
    }

    public ProductConsumptionRequest acceptRequest(Long id) throws BusinessException {
        ProductConsumptionRequest request = get(id);
        productConsumptionService.cosumeProduct(request.getProduct(), request.getQuantity());
        request.setRequestStatus(RequestStatus.ACCEPT);
        return repository.save(request);

    }

    public ProductConsumptionRequest rejectRequest(Long id) {
        ProductConsumptionRequest request = get(id);
        request.setRequestStatus(RequestStatus.REJECT);
        return repository.save(request);
    }
}
