package com.somavk.microservices.inventory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class ErrorResponse {
    private LocalDateTime dateTime;
    private String message;
}
