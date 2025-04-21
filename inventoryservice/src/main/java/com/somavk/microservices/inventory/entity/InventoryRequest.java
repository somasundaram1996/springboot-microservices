package com.somavk.microservices.inventory.entity;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record InventoryRequest(String skuCode, BigDecimal price, int quantity) {
}
