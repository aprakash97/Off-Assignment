package com.microservice.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "UserLoginResponseDto",
        description = "Schema to hold successful login response information"
)
public class UserLoginResponseDto {
    @Schema(
            description = "Access token"
    )
    private String token;
}
