package com.eliasfs06.spring.restapi.stock.manager.restController;

import com.eliasfs06.spring.restapi.stock.manager.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserRestController extends GenericRestController<User> {
}
