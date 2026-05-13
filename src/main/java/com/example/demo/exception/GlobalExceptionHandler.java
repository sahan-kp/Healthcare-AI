package com.example.demo.exception;

import com.example.demo.response.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // VALIDATION ERRORS
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(

            MethodArgumentNotValidException ex

    ){

        String errorMessage = ex
                .getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        ApiResponse response = new ApiResponse(

                false,
                errorMessage

        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    // GENERAL ERRORS
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(

            Exception ex

    ){

        ApiResponse response = new ApiResponse(

                false,
                ex.getMessage()

        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}