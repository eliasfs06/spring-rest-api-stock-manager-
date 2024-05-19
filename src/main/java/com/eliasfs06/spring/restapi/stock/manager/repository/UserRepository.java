package com.eliasfs06.spring.restapi.stock.manager.repository;

import com.eliasfs06.spring.restapi.stock.manager.model.User;

public interface UserRepository extends GenericRepository<User> {
    User findByUsername(String username);
}
