package com.microservice.identityserver.config;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import com.microservice.identityserver.entity.UserCredential;
import com.microservice.identityserver.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> credential = repository.findByName(username);
        return credential.map(CustomUserDetail :: new).orElseThrow(() -> new UsernameNotFoundException("User not found with name: " +username));
    }

}
