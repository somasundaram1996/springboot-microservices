package com.somavk.microservices.product.exception;

import com.somavk.microservices.product.entity.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(value = {ProductNotFoundException.class})
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException exception) {
        return ResponseEntity.ofNullable(ErrorResponse.builder()
                .dateTime(LocalDateTime.now(ZoneOffset.UTC))
                .message(exception.getMessage())
                .build());
    }
}
