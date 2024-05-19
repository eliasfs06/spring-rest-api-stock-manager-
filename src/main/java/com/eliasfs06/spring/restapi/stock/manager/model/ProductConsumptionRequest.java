package com.eliasfs06.spring.restapi.stock.manager.model;

import  com.eliasfs06.spring.restapi.stock.manager.model.enums.RequestStatus;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class ProductConsumptionRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private String description;

    @DateTimeFormat(pattern = "MM-DD-YYYY")
    private Date requestDate;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    public ProductConsumptionRequest() {
    }

    public ProductConsumptionRequest(Product product, Integer quantity, String description) {
        this.product = product;
        this.quantity = quantity;
        this.description = description;
        this.requestDate = new Date();
        this.requestStatus = RequestStatus.WAITING;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
