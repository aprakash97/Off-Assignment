package com.microservice.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name="Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {
    @NotEmpty(message = "Account number cannot be null")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    @Schema(
            description = "Account number of banking system",
            example = "1414141414"
    )
    private Long accountNumber;

    @NotEmpty(message = "Account type cannot be null")
    @Schema(
            description = "Account type of banking system",
            example = "Current"
    )
    private String accountType;

    @NotEmpty(message = "Branch address cannot be null")
    @Schema(
            description = "Branch Address of banking system",
            example = "111 York Street, Colombo"
    )
    private String branchAddress;
}
