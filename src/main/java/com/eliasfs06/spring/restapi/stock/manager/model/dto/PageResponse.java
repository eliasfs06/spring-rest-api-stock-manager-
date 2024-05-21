package com.eliasfs06.spring.restapi.stock.manager.model.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponse<T> {
    private Page<T> data;
    private int currentPage;
    private List<Integer> pageNumbers;

    public PageResponse(Page<T> data, int currentPage, List<Integer> pageNumbers) {
        this.data = data;
        this.currentPage = currentPage;
        this.pageNumbers = pageNumbers;
    }

    public Page<T> getData() {
        return data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }
}
