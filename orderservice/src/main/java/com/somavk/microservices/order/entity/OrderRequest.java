package com.somavk.microservices.order.entity;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderRequest(String skuCode, BigDecimal price, int quantity) {
}
