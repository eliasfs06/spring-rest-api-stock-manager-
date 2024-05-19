package com.eliasfs06.spring.restapi.stock.manager.service.security;

import com.eliasfs06.spring.restapi.stock.manager.model.dto.AuthenticationDTO;
import com.eliasfs06.spring.restapi.stock.manager.model.User;
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
    public Authentication authenticateUser(AuthenticationDTO data){
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());
        Authentication auth = this.authenticationManager.authenticate(user);
        return auth;
    }

    public String getToken(Authentication auth){
        return tokenService.generateToken((User) auth.getPrincipal());
    }
}
