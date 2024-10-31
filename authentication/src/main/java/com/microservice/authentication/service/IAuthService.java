package com.microservice.authentication.service;


import com.microservice.authentication.dto.UserLoginDto;
import com.microservice.authentication.dto.UserLoginResponseDto;
import com.microservice.authentication.dto.UserRegisterDto;

public interface IAuthService {

    public void userRegister(UserRegisterDto userRegisterDto);

    public UserLoginResponseDto userLogin(UserLoginDto userLoginDto);
}
