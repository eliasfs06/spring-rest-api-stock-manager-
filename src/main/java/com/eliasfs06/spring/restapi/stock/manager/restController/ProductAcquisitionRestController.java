package com.eliasfs06.spring.restapi.stock.manager.restController;

import com.eliasfs06.spring.restapi.stock.manager.model.Product;
import com.eliasfs06.spring.restapi.stock.manager.model.ProductAcquisition;
import com.eliasfs06.spring.restapi.stock.manager.model.ProductAcquisitionItem;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.PageResponse;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.ProductAcquisitionItemDTO;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.ProductAcquisitionItemListDTO;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.ResponseWrapper;
import com.eliasfs06.spring.restapi.stock.manager.model.exceptionsHandler.BusinessException;
import com.eliasfs06.spring.restapi.stock.manager.service.ProductAcquisitionService;
import com.eliasfs06.spring.restapi.stock.manager.service.ProductService;
import com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageCode;
import com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("api/product-acquisition")
public class ProductAcquisitionRestController extends GenericRestController<ProductAcquisition> {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAcquisitionService service;

    @Autowired
    private MessageHelper messageHelper;

    private final int DEFAULT_PAGE_SIZE = 5;
    private final int DEFAULT_PAGE_NUMBER = 1;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<Long>> delete(@PathVariable Long id) {
        try {
            service.deleteAcquisition(id);
            return new ResponseEntity<>(new ResponseWrapper<>(MessageCode.DEFAULT_SUCCESS_MSG, "success", id), HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = messageHelper.getMessage(MessageCode.DEFAULT_ERROR_MSG);
            return new ResponseEntity<>(new ResponseWrapper<>(errorMessage, "error",null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll(@RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<ProductAcquisition> productAcquisitionPage = service.getPage(PageRequest.of(currentPage - 1, pageSize));

        if (productAcquisitionPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        int totalPages = productAcquisitionPage.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageResponse<>(productAcquisitionPage, currentPage, pageNumbers));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductAcquisitionItemListDTO productAcquisitionItemListDTO) {
        ProductAcquisition productAcquisitionSaved = service.save(productAcquisitionItemListDTO);
        return new ResponseEntity<>(new ResponseWrapper<>(messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG), "success", productAcquisitionSaved), HttpStatus.CREATED);
    }
}