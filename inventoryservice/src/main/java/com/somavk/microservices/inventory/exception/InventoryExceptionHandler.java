package com.somavk.microservices.inventory.exception;

import com.somavk.microservices.inventory.entity.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ControllerAdvice
public class InventoryExceptionHandler {

    @ExceptionHandler(value = {InventoryNotFoundException.class})
    public ResponseEntity<Object> handleInventoryNotFoundException(InventoryNotFoundException exception) {
        return ResponseEntity.ofNullable(ErrorResponse.builder()
                .dateTime(LocalDateTime.now(ZoneOffset.UTC))
                .message(exception.getMessage())
                .build());
    }
}
