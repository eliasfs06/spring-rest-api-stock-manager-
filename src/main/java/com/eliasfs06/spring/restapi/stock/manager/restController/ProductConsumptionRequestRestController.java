package com.eliasfs06.spring.restapi.stock.manager.restController;

import com.eliasfs06.spring.restapi.stock.manager.model.ProductConsumptionRequest;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.ResponseWrapper;
import com.eliasfs06.spring.restapi.stock.manager.model.exceptionsHandler.BusinessException;
import com.eliasfs06.spring.restapi.stock.manager.service.ProductConsumptionRequestService;
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
@RequestMapping("api/product-consumption-requests")
public class ProductConsumptionRequestRestController extends GenericRestController<ProductConsumptionRequest> {

    @Autowired
    private ProductConsumptionRequestService service;

    @Autowired
    private MessageHelper messageHelper;

    private final int DEFAULT_PAGE_SIZE = 5;
    private final int DEFAULT_PAGE_NUMBER = 1;

    @GetMapping("/list")
    public ResponseEntity<?> findAll(@RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<ProductConsumptionRequest> requestPage = service.getPage(PageRequest.of(currentPage - 1, pageSize));
        return ResponseEntity.ok(requestPage);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<ResponseWrapper<ProductConsumptionRequest>> accept(@PathVariable Long id) {
        try {
            ProductConsumptionRequest request = service.acceptRequest(id);
            return new ResponseEntity<>(new ResponseWrapper<>(messageHelper.getMessage(MessageCode.PRODUCT_CONSUMPTION_REQUEST_ACCEPTED), "success", request), HttpStatus.ACCEPTED);
        } catch (BusinessException e) {
            String errorMessage = messageHelper.getMessage(MessageCode.CANT_CONSUME_PRODUCT);
            return new ResponseEntity<>(new ResponseWrapper<>(errorMessage, "error", null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<ResponseWrapper<ProductConsumptionRequest>> reject(@PathVariable Long id) {
        ProductConsumptionRequest request = service.rejectRequest(id);
        return new ResponseEntity<>(new ResponseWrapper<>(messageHelper.getMessage(MessageCode.PRODUCT_CONSUMPTION_REQUEST_DENIED), "success", request), HttpStatus.ACCEPTED);
    }
}
