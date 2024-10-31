package com.microservice.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
public class ResponseDto {
    @Schema(
            description = "status code"
    )
    private  int statusCode;
    @Schema(
            description = "status message"
    )
    private String statusMsg;

}
