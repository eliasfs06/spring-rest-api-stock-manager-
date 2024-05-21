package com.eliasfs06.spring.restapi.stock.manager.restController;

import com.eliasfs06.spring.restapi.stock.manager.model.dto.AuthenticationDTO;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.RegisterDTO;
import com.eliasfs06.spring.restapi.stock.manager.model.dto.ResponseWrapper;
import com.eliasfs06.spring.restapi.stock.manager.model.exceptionsHandler.BusinessException;
import com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageCode;
import com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageHelper;
import com.eliasfs06.spring.restapi.stock.manager.service.security.AuthenticationService;
import com.eliasfs06.spring.restapi.stock.manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthenticationRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private MessageHelper messageHelper;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data){
        try {
            Authentication auth = authenticationService.authenticateUser(data);
            String token = authenticationService.getToken(auth);
            return new ResponseEntity<>(new ResponseWrapper<>(MessageCode.DEFAULT_SUCCESS_MSG, "success",token), HttpStatus.ACCEPTED);

        } catch (BusinessException e) {
            String errorMessage = messageHelper.getMessage(e.getMessage());
            return new ResponseEntity<>(new ResponseWrapper<>(errorMessage, "error",null), HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new ResponseWrapper<>(e.getMessage(), "error",null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper<RegisterDTO>> register(@RequestBody RegisterDTO userData) {
        try {
            userService.registerUser(userData);
        } catch (BusinessException e) {
            String errorMessage = messageHelper.getMessage(e.getMessage());
            return new ResponseEntity<>(new ResponseWrapper<>(errorMessage, "error",null), HttpStatus.BAD_REQUEST);
        }

        String successMessage = messageHelper.getMessage(MessageCode.DEFAULT_SUCCESS_MSG);
        return new ResponseEntity<>(new ResponseWrapper<>(successMessage, "success", userData), HttpStatus.CREATED);
    }
}


