package com.microservice.identityserver.service;

import com.microservice.identityserver.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.identityserver.entity.UserCredential;
import com.microservice.identityserver.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredential credential){
        Optional<UserCredential> existingUser = repository.findByName(credential.getName());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("Username already exists, Register with another username");
        }

        if(credential.getName().equals("Admin")){
            credential.setRole("ADMIN");
        }
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "User Added to the System";
    }

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
