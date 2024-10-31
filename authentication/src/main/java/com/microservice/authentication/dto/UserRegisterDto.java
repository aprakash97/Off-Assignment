package com.microservice.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(
        name = "UserRegisterDto",
        description = "Schema to hold User information"
)
public class UserRegisterDto {
    @Schema(description = "name")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Schema(description = "username")
    @NotEmpty(message = "username cannot be empty")
    private String username;

    @Schema(description = "password")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

}
