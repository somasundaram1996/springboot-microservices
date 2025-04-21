package com.somavk.microservices.order.entity;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse(int id, String orderNumber, String skuCode, BigDecimal price, int quantity) {

}
