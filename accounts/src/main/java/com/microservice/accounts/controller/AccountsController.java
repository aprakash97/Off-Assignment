package com.microservice.accounts.controller;

import com.microservice.accounts.constants.AccountsConstants;
import com.microservice.accounts.dto.AccountsContactInfoDto;
import com.microservice.accounts.dto.CustomerDto;
import com.microservice.accounts.dto.ErrorResponseDto;
import com.microservice.accounts.dto.ResponseDto;
import com.microservice.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "Accounts",
        description = "CRUD REST APIs for Accounts Microservice"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
//@AllArgsConstructor
@Validated
public class AccountsController {

    //Should be revert back as normal variable
    private final IAccountsService iAccountsService;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    //should be deleted after configuration
    public AccountsController(IAccountsService iAccountsService){
        this.iAccountsService = iAccountsService;
    }

    @Value("${build.version}")
    private String buildVersion;

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
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                               String mobileNumber){
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        System.out.println("customerDto" + customerDto);
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
                                                            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
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

    @GetMapping("/build-info")
    @Operation(
            summary = "Get Build Version REST API",
            description = "REST API to get Build Version"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Created"
    )
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/java-version")
    @Operation(
            summary = "Get Java Version REST API",
            description = "REST API to get Java Version"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Created"
    )
    public ResponseEntity<String> getJavaInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("MAVEN_HOME"));
    }

    @GetMapping("/contact-info")
    @Operation(
            summary = "Get Contact info REST API",
            description = "REST API to get Contact Information"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Created"
    )
    public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }
}
