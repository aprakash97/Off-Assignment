package com.microservice.authentication.controller;

import com.microservice.authentication.constant.AuthenticationConstant;
import com.microservice.authentication.dto.*;
import com.microservice.authentication.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(
        name = "Auth",
        description = "AUth REST APIs"
)
@RestController
@RequestMapping(path = "/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@AllArgsConstructor
public class AuthenticationController {

    @Autowired
    private IAuthService authService;

    @Operation(
            summary = "User register",
            description = "REST API to user login"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Success"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> userRegister(@Valid @RequestBody UserRegisterDto userRegisterDto){
        authService.userRegister(userRegisterDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AuthenticationConstant.CREATED_STATUS_CODE,AuthenticationConstant.USER_REGISTER_STATUS_MESSAGE));
    }

    @Operation(
            summary = "User login",
            description = "REST API to user login"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Success"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status Unauthorized Request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> userLogin(@Valid @RequestBody UserLoginDto userLoginDto){
        UserLoginResponseDto responseDto = authService.userLogin(userLoginDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);

    }

}
