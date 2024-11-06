package com.microservice.loans.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.microservice.loans.constants.LoansConstants;
import com.microservice.loans.dto.ErrorResponseDto;
import com.microservice.loans.dto.ResponseDto;
import com.microservice.loans.service.IPromotionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(path = "/promotions", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(
        name = "Promotion",
        description = "CRUD REST APIs for Promotions Microservice"
)
public class PromotionsController {
    private IPromotionsService iPromotionsService;

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
                .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201_PROMOTION));
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
