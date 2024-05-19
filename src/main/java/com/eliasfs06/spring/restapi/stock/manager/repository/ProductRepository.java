package com.eliasfs06.spring.restapi.stock.manager.repository;

import  com.eliasfs06.spring.restapi.stock.manager.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends GenericRepository<Product> {
}
