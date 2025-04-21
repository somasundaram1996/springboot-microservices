package com.somavk.microservices.product.entity;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(int id, String name, String description, BigDecimal price) {

}
