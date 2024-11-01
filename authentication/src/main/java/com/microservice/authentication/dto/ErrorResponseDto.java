package com.microservice.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {
    private HttpStatus errorCode;
    private String errorMessage;
}