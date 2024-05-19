package com.eliasfs06.spring.restapi.stock.manager.model;

import jakarta.persistence.*;

@Entity
public class ProductAcquisitionItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "productAcquisition_id")
    private ProductAcquisition productAcquisition;

    public ProductAcquisitionItem() {
    }

    public ProductAcquisitionItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductAcquisitionItem(ProductAcquisition productAcquisition) {
        this.productAcquisition = productAcquisition;
    }

    public ProductAcquisitionItem(Product product, Integer quantity, ProductAcquisition productAcquisition) {
        this.product = product;
        this.quantity = quantity;
        this.productAcquisition = productAcquisition;
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

    public ProductAcquisition getProductAcquisition() {
        return productAcquisition;
    }

    public void setProductAcquisition(ProductAcquisition productAcquisition) {
        this.productAcquisition = productAcquisition;
    }
}
