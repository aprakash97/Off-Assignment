package com.microservice.accounts.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.microservice.accounts.constants.AccountsConstants;
import com.microservice.accounts.dto.CustomerDto;
import com.microservice.accounts.dto.ErrorResponseDto;
import com.microservice.accounts.dto.ResponseDto;
import com.microservice.accounts.service.IAccountsService;
import com.microservice.accounts.service.IPromotionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Tag(
        name = "Accounts",
        description = "CRUD REST APIs for Accounts Microservice"
)
@RestController
@RequestMapping(path="/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountsController {

    private IAccountsService iAccountsService;
    private IPromotionsService iPromotionsService;

    @PostMapping("/create")
    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer &  Account inside Bank System"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status Created"
    )
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    @Operation(
            summary = "Get Account REST API",
            description = "REST API to get Customer in Bank System"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Created"
    )
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{12})",message = "NIC number must be 10 digits")
                                                               String nicNumber){
        CustomerDto customerDto = iAccountsService.fetchAccount(nicNumber);
        return ResponseEntity
                .status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    @Operation(
            summary = "Update Account REST API",
            description = "REST API to update Customer in Bank System"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    @Operation(
            summary = "Delete Account REST API",
            description = "REST API to delete Customer in Bank System"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp="(^$|[0-9]{12})",message = "NIC number must be 12 digits")
                                                            String nicNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(nicNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Create Promotion REST API",
            description = "REST API to create new promotion inside Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )

    @PostMapping("/createPromotion")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam
                                                  @Pattern(regexp = "^.{10,100}$",message = "Promotional title should be exceeded")
                                                  String promotionalTitle) {
        System.out.println("Testing in controller" + promotionalTitle);
        iPromotionsService.createPromotion(promotionalTitle);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201_PROMOTION));
    }

    @Operation(
            summary = "Get Promotion REST API",
            description = "REST API to get all promotions list as PDF"
    )
    @GetMapping(value = "/getPromotions", produces = MediaType.APPLICATION_PDF_VALUE)
    public void getAllPromotionsAsPdf(HttpServletResponse response) throws DocumentException, IOException {
        List<String> promotions = iPromotionsService.getAllPromotions();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=promotions.pdf");

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();
        document.add(new Paragraph("Promotions List"));
        document.add(new Paragraph(" "));

        for (String promotion : promotions) {
            document.add(new Paragraph(promotion));
        }

        document.close();

        response.getOutputStream().write(out.toByteArray());
        response.getOutputStream().flush();
    }
}
