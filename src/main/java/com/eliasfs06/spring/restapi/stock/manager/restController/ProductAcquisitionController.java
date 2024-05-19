package com.eliasfs06.spring.restapi.stock.manager.restController;

import  com.eliasfs06.spring.restapi.stock.manager.model.Product;
import  com.eliasfs06.spring.restapi.stock.manager.model.ProductAcquisition;
import  com.eliasfs06.spring.restapi.stock.manager.model.ProductAcquisitionItem;
import  com.eliasfs06.spring.restapi.stock.manager.model.dto.ProductAcquisitionItemDTO;
import  com.eliasfs06.spring.restapi.stock.manager.model.exceptionsHandler.BusinessException;
import  com.eliasfs06.spring.restapi.stock.manager.service.ProductAcquisitionService;
import  com.eliasfs06.spring.restapi.stock.manager.service.ProductService;
import  com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageCode;
import  com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("product-acquisition")
public class ProductAcquisitionController extends GenericRestController<ProductAcquisition> {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAcquisitionService service;

    @Autowired
    private MessageHelper messageHelper;

    private final String FORM_PATH = "/product-acquisition/form";
    private final String LIST_PATH = "/product-acquisition/list";
    private final int DEFAULT_PAGE_SIZE = 5;
    private final int DEFAULT_PAGE_NUMBER = 1;

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
        try {
            service.deleteAcquisition(id);
            model.addAttribute("successMessage", messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG));
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", messageHelper.getMessage(MessageCode.CANT_DELETE_ACQUISITION));
        }
        return findAll(model, Optional.of(DEFAULT_PAGE_NUMBER), Optional.of(DEFAULT_PAGE_SIZE));
    }

    @GetMapping("/list")
    public String findAll(Model model, @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<ProductAcquisition> productAcquisitionPage = service.getPage(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("productAcquisitionList", productAcquisitionPage);
        model.addAttribute("currentPage", currentPage);

        int totalPages = productAcquisitionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return LIST_PATH;
    }

    @PostMapping("/save")
    public String save(Model model, ProductAcquisition productAcquisition, @RequestBody List<ProductAcquisitionItemDTO> productAcquisitionItens) {
        service.create(productAcquisition, productAcquisitionItens);
        model.addAttribute("successMessage", messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG));
        return findAll(model, Optional.of(DEFAULT_PAGE_NUMBER), Optional.of(DEFAULT_PAGE_SIZE));
    }

    @PostMapping("/add-item")
    public String addProduct(Model model, @RequestBody List<ProductAcquisitionItemDTO> productAcquisitionItens) {

        List<ProductAcquisitionItem> itens = new ArrayList<>();
        if(productAcquisitionItens != null && !productAcquisitionItens.isEmpty()){
            productAcquisitionItens.forEach(item -> {
                Product product = productService.get(Long.valueOf(item.getProductId()));
                ProductAcquisitionItem newItem = new ProductAcquisitionItem(product, Integer.valueOf(item.getQuantity()));
                itens.add(newItem);
            });
        }
        model.addAttribute("itens", itens);

        return "/product-acquisition/product-acquisition-itens :: acquisition-itens";
    }
}

