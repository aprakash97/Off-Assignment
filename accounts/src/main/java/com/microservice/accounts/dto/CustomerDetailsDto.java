package com.microservice.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name="Customer Details",
        description = "Schema to hold Customer, Account and Loan information"
)
public class CustomerDetailsDto {
    @NotEmpty(message = "Name cannot be null")
    @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30")
    @Schema(
            description = "Name of Customer",
            example = "Tom"
    )
    private String name;

    @NotEmpty(message = "Email cannot be null")
    @Email(message = "Email address should be a valid value")
    @Schema(
            description = "Email of Customer",
            example = "Tom@gmail.com"
    )
    private String email;

    @Pattern(regexp = "(^$|[0-9]{12})", message = "NIC number must be 12 digits")
    @Schema(
            description = "NIC number of Customer",
            example = "198201409894"
    )
    private String nicNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Loans details of the Customer"
    )
    private LoansDto loansDto;
}
