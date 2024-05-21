package com.eliasfs06.spring.restapi.stock.manager.service;

import  com.eliasfs06.spring.restapi.stock.manager.model.Product;
import  com.eliasfs06.spring.restapi.stock.manager.model.ProductAcquisition;
import  com.eliasfs06.spring.restapi.stock.manager.model.ProductAcquisitionItem;
import com.eliasfs06.spring.restapi.stock.manager.model.User;
import  com.eliasfs06.spring.restapi.stock.manager.model.dto.ProductAcquisitionItemDTO;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.ProductAcquisitionItemListDTO;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.ProductAcquisitionItemListResponseDTO;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.ProductAcquisitionItemResponseDTO;
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

    @Autowired
    private UserService userService;

    public Page<ProductAcquisitionItemListResponseDTO> getPageResponse(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ProductAcquisitionItemListResponseDTO> list;
        List<ProductAcquisition> productAcquisitions = this.findAll();
        List<ProductAcquisitionItemListResponseDTO> productAcquisitionResponseList = new ArrayList<>();

        productAcquisitions.forEach(productAcquisition -> {
            productAcquisition.setItens(productAcquisitionItemService.findByProductAcquisition(productAcquisition.getId()));

            ProductAcquisitionItemListResponseDTO productAcquisitionResponse = new ProductAcquisitionItemListResponseDTO();

            productAcquisition.getItens().forEach(item -> {
                ProductAcquisitionItemResponseDTO itemDTO = new ProductAcquisitionItemResponseDTO(item.getProduct(), item.getQuantity());
                productAcquisitionResponse.getProductAcquisitionItens().add(itemDTO);
                productAcquisitionResponse.setUserId(item.getProductAcquisition().getAcquisitionUser().getId());
            });

            productAcquisitionResponseList.add(productAcquisitionResponse);
        });

        if (productAcquisitionResponseList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, productAcquisitionResponseList.size());
            list = productAcquisitionResponseList.subList(startItem, toIndex);
        }

        return new PageImpl<ProductAcquisitionItemListResponseDTO>(list, PageRequest.of(currentPage, pageSize), productAcquisitionResponseList.size());
    }

    public List<ProductAcquisition> findAll(){
        return repository.findAll();
    }

    public ProductAcquisitionItemListDTO save(ProductAcquisitionItemListDTO productAcquisitionList) throws BusinessException {
        if(productAcquisitionList.getProductAcquisitionItens().isEmpty()){
            throw new BusinessException(MessageCode.DEFAULT_EMPTY_FIELD_MSG);
        }

        ProductAcquisition productAcquisition = new ProductAcquisition();
        productAcquisition.setAquisitionDate(new Date());

        User user = userService.findById(productAcquisitionList.getUserId());
        productAcquisition.setAcquisitionUser(user);

        repository.save(productAcquisition);

        List<ProductAcquisitionItem> itens = new ArrayList<>();
        productAcquisitionList.getProductAcquisitionItens().forEach(item -> {
            Product product = productService.get(Long.valueOf(item.getProductId()));
            ProductAcquisitionItem newItem = new ProductAcquisitionItem(product, item.getQuantity(), productAcquisition);
            productAcquisitionItemService.create(newItem);
            itens.add(newItem);
        });

        productAcquisition.setItens(itens);

        return productAcquisitionList;
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
