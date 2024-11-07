package com.microservice.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(
        name="Loans",
        description = "Schema to hold Loans information"
)
public class LoansDto {

    @NotEmpty(message = "NIC Number cannot be empty or null")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "NIC number must be 12 digits")
    @Schema(
            description = "NIC number of customer",
            example = "198201409894"
    )
    private String nicNumber;

    @NotEmpty(message = "Loan Number cannot be empty or null")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Loan number must be 12 digits")
    @Schema(
            description = "Loan number of customer",
            example = "101010101000"
    )
    private String loanNumber;

    @NotEmpty(message = "Loan type cannot be null")
    @Schema(
            description = "Type of the loan", example = "Home Loan"
    )
    private String loanType;

    @Positive(message = "Total loan amount should be greater than zero")
    @Schema(
            description = "Total loan amount", example = "100000"
    )
    private int totalLoan;

    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    @Schema(
            description = "Total loan amount paid", example = "1000"
    )
    private int amountPaid;

    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    @Schema(
            description = "Total outstanding amount against a loan", example = "99000"
    )
    private int outstandingAmount;
}
