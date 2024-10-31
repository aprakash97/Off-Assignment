package com.microservice.authentication.exception;

import com.microservice.authentication.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
//        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST,
                validationErrorList.get(0).getDefaultMessage()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,
                                                                  WebRequest webRequest) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentialsException(BadCredentialsException exception,
                                                                         WebRequest webRequest) {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                HttpStatus.UNAUTHORIZED,
                exception.getMessage()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException usernameAlreadyExistsException, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST,
                usernameAlreadyExistsException.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.NOT_FOUND,
                resourceNotFoundException.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
}
