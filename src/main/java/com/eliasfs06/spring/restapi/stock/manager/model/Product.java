package com.eliasfs06.spring.restapi.stock.manager.model;

import  com.eliasfs06.spring.restapi.stock.manager.model.enums.Brand;
import  com.eliasfs06.spring.restapi.stock.manager.model.enums.ProductType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Brand brand;

    private ProductType type;

    private String description;

    private Integer stockQuantity;

    private Boolean freeToConsume = true;

    public Product() {
    }

    public Product(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Boolean getFreeToConsume() {
        return freeToConsume;
    }

    public void setFreeToConsume(Boolean freeToConsume) {
        this.freeToConsume = freeToConsume;
    }
}
