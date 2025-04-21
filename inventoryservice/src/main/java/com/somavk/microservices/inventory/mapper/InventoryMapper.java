package com.somavk.microservices.inventory.mapper;

import com.somavk.microservices.inventory.entity.InventoryRequest;
import com.somavk.microservices.inventory.entity.InventoryResponse;
import com.somavk.microservices.inventory.model.Inventory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryMapper {

    public static InventoryResponse toResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .build();
    }

    public static Inventory toDto(InventoryRequest inventoryRequest) {
        return Inventory.builder()
                .skuCode(inventoryRequest.skuCode())
                .quantity(inventoryRequest.quantity())
                .build();
    }
}
