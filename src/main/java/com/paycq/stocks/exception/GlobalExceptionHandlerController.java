package com.paycq.stocks.exception;

import java.io.IOException;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Amit Chandra
 * @version 1.0
 * @since 2023-01-09
 * 
 * Centralized exception handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @Bean
    public ErrorAttributes errorAttributes() {
        // Hide exception field in the return object
        return new DefaultErrorAttributes() {

        };
    }

    @ExceptionHandler(StockException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(StockException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ex.getHttpStatus().value())
                .errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(ex.getHttpStatus().value()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
    }

}
