package com.eliasfs06.spring.restapi.stock.manager.restController;

import com.eliasfs06.spring.restapi.stock.manager.model.dto.AuthenticationDTO;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.RegisterDTO;
import com.eliasfs06.spring.restapi.stock.manager.model.exceptionsHandler.BusinessException;
import com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageCode;
import com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageHelper;
import com.eliasfs06.spring.restapi.stock.manager.service.security.AuthenticationService;
import com.eliasfs06.spring.restapi.stock.manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private MessageHelper messageHelper;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        Authentication auth = authenticationService.authenticateUser(data);
        String token = authenticationService.getToken(auth);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO userData){
        try {
            userService.registerUser(userData);
        } catch (BusinessException e){
            return new ResponseEntity<>(messageHelper.getMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG), HttpStatus.CREATED);
    }
}


