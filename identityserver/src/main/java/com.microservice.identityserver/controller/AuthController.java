package com.microservice.identityserver.controller;

import com.microservice.identityserver.dto.AuthRequest;
import com.microservice.identityserver.entity.UserCredential;
import com.microservice.identityserver.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user){
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        Authentication auth =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(auth.isAuthenticated()){
            return service.generateToken(authRequest.getUsername());
        }else{
            throw new RuntimeException("Username not found");
        }

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        service.validateToken(token);
        return "Token is valid";
    }
}
