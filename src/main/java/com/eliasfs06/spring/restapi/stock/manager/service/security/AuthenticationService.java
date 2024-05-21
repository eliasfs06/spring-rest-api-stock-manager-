package com.eliasfs06.spring.restapi.stock.manager.service.security;

import com.eliasfs06.spring.restapi.stock.manager.model.dto.AuthenticationDTO;
import com.eliasfs06.spring.restapi.stock.manager.model.User;
import com.eliasfs06.spring.restapi.stock.manager.model.exceptionsHandler.BusinessException;
import com.eliasfs06.spring.restapi.stock.manager.service.helper.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    public Authentication authenticateUser(AuthenticationDTO data) throws BusinessException {
        if(data.getUsername().isEmpty() || data.getPassword().isEmpty()){
            throw new BusinessException(MessageCode.DEFAULT_EMPTY_FIELD_MSG);
        }
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        Authentication auth = this.authenticationManager.authenticate(user);
        return auth;
    }

    public String getToken(Authentication auth){
        return tokenService.generateToken((User) auth.getPrincipal());
    }
}
