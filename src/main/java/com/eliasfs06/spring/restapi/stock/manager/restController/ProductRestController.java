package com.eliasfs06.spring.restapi.stock.manager.restController;

import com.eliasfs06.spring.restapi.stock.manager.model.Product;
import com.eliasfs06.spring.restapi.stock.manager.model.ProductConsumption;
import com.eliasfs06.spring.restapi.stock.manager.model.ProductConsumptionRequest;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.ResponseWrapper;
import com.eliasfs06.spring.restapi.stock.manager.model.exceptionsHandler.BusinessException;
import com.eliasfs06.spring.restapi.stock.manager.service.ProductConsumptionRequestService;
import com.eliasfs06.spring.restapi.stock.manager.service.ProductConsumptionService;
import com.eliasfs06.spring.restapi.stock.manager.service.ProductService;
import com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageCode;
import com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/products")
public class ProductRestController extends GenericRestController<Product> {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConsumptionService productConsumptionService;

    @Autowired
    private ProductConsumptionRequestService productConsumptionRequestService;

    @Autowired
    private MessageHelper messageHelper;

    private final int DEFAULT_PAGE_SIZE = 5;
    private final int DEFAULT_PAGE_NUMBER = 1;

    @GetMapping("/list")
    public ResponseEntity<?> findAll(@RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<Product> productPage = productService.getPage(PageRequest.of(currentPage - 1, pageSize));

        return ResponseEntity.ok(productPage);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Product product) {
        try {
            productService.validateProduct(product);
            Product productSaved = productService.create(product);
            return new ResponseEntity<>(new ResponseWrapper<>(messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG), "success", productSaved), HttpStatus.CREATED);
        } catch (BusinessException e) {
            String errorMessage = messageHelper.getMessage(MessageCode.DEFAULT_EMPTY_FIELD_MSG);
            return new ResponseEntity<>(new ResponseWrapper<>(errorMessage, "error",null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<Long>> delete(@PathVariable Long id) {
        try {
            productService.delete(id);
            return new ResponseEntity<>(new ResponseWrapper<>(messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG), "success", id), HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = messageHelper.getMessage(MessageCode.DEFAULT_ERROR_MSG);
            return new ResponseEntity<>(new ResponseWrapper<>(errorMessage, "error",null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/consume/{id}")
    public ResponseEntity<ResponseWrapper<ProductConsumption>> consume(@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            ProductConsumption productConsumption = productConsumptionService.cosumeProduct(productService.get(id), quantity);
            return new ResponseEntity<>(new ResponseWrapper<>(messageHelper.getMessage(MessageCode.PRODUCT_SUCCESSFULLY_CONSUMED), "success", productConsumption), HttpStatus.ACCEPTED);

        } catch (BusinessException e) {
            String errorMessage = messageHelper.getMessage(MessageCode.CANT_CONSUME_PRODUCT);
            return new ResponseEntity<>(new ResponseWrapper<>(errorMessage, "error",null), HttpStatus.BAD_REQUEST);        }
    }

    @PostMapping("/request-to-consume/{id}")
    public ResponseEntity<ResponseWrapper<ProductConsumptionRequest>> requestToConsume(@PathVariable Long id,
                                                   @RequestParam Integer quantity, String description) {
        try {
            ProductConsumptionRequest productConsumptionReq = productConsumptionRequestService.requestToCosumeProduct(productService.get(id), quantity, description);
            return new ResponseEntity<>(new ResponseWrapper<>(messageHelper.getMessage(MessageCode.PRODUCT_CONSUMPTION_REQUESTED_SUCCESSFULLY), "success", productConsumptionReq), HttpStatus.ACCEPTED);

        } catch (BusinessException e) {
            String errorMessage = messageHelper.getMessage(MessageCode.CANT_CONSUME_PRODUCT);
            return new ResponseEntity<>(new ResponseWrapper<>(errorMessage, "error",null), HttpStatus.BAD_REQUEST);        }
    }

}