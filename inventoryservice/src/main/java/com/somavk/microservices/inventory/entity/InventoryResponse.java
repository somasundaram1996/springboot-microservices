package com.somavk.microservices.inventory.entity;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record InventoryResponse(int id, String skuCode, int quantity) {

}
