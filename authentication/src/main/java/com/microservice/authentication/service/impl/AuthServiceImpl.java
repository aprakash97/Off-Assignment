package com.microservice.authentication.service.impl;

import com.microservice.authentication.dto.UserLoginDto;
import com.microservice.authentication.dto.UserLoginResponseDto;
import com.microservice.authentication.dto.UserRegisterDto;
import com.microservice.authentication.entity.User;
import com.microservice.authentication.repo.UserRepo;
import com.microservice.authentication.service.IAuthService;
import com.microservice.authentication.service.JwtService;
import com.microservice.authentication.utility.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    @Override
    public void userRegister(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setName(userRegisterDto.getName());
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(encoder.encode(userRegisterDto.getPassword()));
        user.setUserRole(UserRole.USER);
        userRepo.save(user);
    }

    @Override
    public UserLoginResponseDto userLogin(UserLoginDto userLoginDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));


        if(authentication.isAuthenticated()){
            UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
            User user = userRepo.findByUsername(userLoginDto.getUsername());
            userLoginResponseDto.setToken(jwtService.generateToken(userLoginDto.getUsername(),user.getUserId(), String.valueOf(user.getUserRole())));
            return userLoginResponseDto;
        }else{
            System.out.println("Login failed");
            throw new RuntimeException("Login failed");
        }

    }
}
