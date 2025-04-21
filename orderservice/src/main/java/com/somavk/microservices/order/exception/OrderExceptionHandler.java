package com.somavk.microservices.order.exception;

import com.somavk.microservices.order.entity.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(value = {OrderNotFoundException.class, ProductNotInStockException.class})
    public ResponseEntity<Object> handleOrderNotFoundException(Exception exception) {
        return ResponseEntity.ofNullable(ErrorResponse.builder()
                .dateTime(LocalDateTime.now(ZoneOffset.UTC))
                .message(exception.getMessage())
                .build());
    }

}
