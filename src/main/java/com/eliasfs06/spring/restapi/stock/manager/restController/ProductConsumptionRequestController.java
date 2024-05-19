package com.eliasfs06.spring.restapi.stock.manager.restController;

import  com.eliasfs06.spring.restapi.stock.manager.model.ProductConsumptionRequest;
import  com.eliasfs06.spring.restapi.stock.manager.model.exceptionsHandler.BusinessException;
import  com.eliasfs06.spring.restapi.stock.manager.service.ProductConsumptionRequestService;
import  com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageCode;
import  com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("product-consumption-request")
public class ProductConsumptionRequestController extends GenericRestController<ProductConsumptionRequest> {

    @Autowired
    private ProductConsumptionRequestService service;

    @Autowired
    private MessageHelper messageHelper;

    private final String LIST_PATH = "/product-consumption-request/list";
    private final int DEFAULT_PAGE_SIZE = 5;
    private final int DEFAULT_PAGE_NUMBER = 1;

    @GetMapping("/list")
    public String findAll(Model model, @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(DEFAULT_PAGE_NUMBER);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<ProductConsumptionRequest> requestPage = service.getPage(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("requestList", requestPage);
        model.addAttribute("currentPage", currentPage);

        int totalPages = requestPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return LIST_PATH;
    }

    @GetMapping("/accept/{id}")
    public String accept(Model model, @PathVariable Long id){
        try {
            service.acceptRequest(id);
            model.addAttribute("successMessage", messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG));
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", messageHelper.getMessage(MessageCode.CANT_CONSUME_PRODUCT));
        }
        return findAll(model, Optional.of(DEFAULT_PAGE_NUMBER), Optional.of(DEFAULT_PAGE_SIZE));
    }

    @GetMapping("/reject/{id}")
    public String reject(Model model, @PathVariable Long id){
        service.rejectRequest(id);
        model.addAttribute("successMessage", messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG));

        return findAll(model, Optional.of(DEFAULT_PAGE_NUMBER), Optional.of(DEFAULT_PAGE_SIZE));
    }
}
