package com.eliasfs06.spring.restapi.stock.manager.repository;

import com.eliasfs06.spring.restapi.stock.manager.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
}