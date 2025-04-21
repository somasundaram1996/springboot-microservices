package com.somavk.microservices.product.entity;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequest(int id, String name, String description, BigDecimal price) {
}
