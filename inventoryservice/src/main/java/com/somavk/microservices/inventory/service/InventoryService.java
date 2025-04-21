package com.somavk.microservices.inventory.service;

import com.somavk.microservices.inventory.entity.InventoryRequest;
import com.somavk.microservices.inventory.entity.InventoryResponse;
import com.somavk.microservices.inventory.exception.InventoryNotFoundException;
import com.somavk.microservices.inventory.mapper.InventoryMapper;
import com.somavk.microservices.inventory.model.Inventory;
import com.somavk.microservices.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean createInventoryEntry(List<InventoryRequest> inventoryRequest) {
        List<Inventory> objToSave = inventoryRequest.stream().map(InventoryMapper::toDto).toList();
        inventoryRepository.saveAllAndFlush(objToSave);
        log.info("Inventory saved to Database");
        return true;
    }

    public List<InventoryResponse> getAllInventory() {
        log.info("Retrieving saved Inventory from Database");
        return inventoryRepository.findAll().stream().map(InventoryMapper::toResponse).toList();
    }

    public InventoryResponse getInventoryBySkuCode(String skuCode) {
        log.info("Retrieving saved Inventory from Database for a specific id");
        return InventoryMapper.toResponse(inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new InventoryNotFoundException("The inventory Item is not present")));
    }

    public void updateInventoryBySkuCode(String skuCode, int quantity) {
        Inventory item = inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new InventoryNotFoundException("The inventory Item is not present"));
        item.setQuantity(quantity);
        inventoryRepository.saveAndFlush(item);

    }

    public boolean checkStockBySkuCode(String skuCode, int quantity) {
        Inventory item = inventoryRepository.findBySkuCode(skuCode).orElse(null);
        return item != null && item.getQuantity() >= quantity;

    }

    public void reduceStockInventoryBySkuCode(String skuCode, int quantity) {
        Inventory item = inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new InventoryNotFoundException("The inventory Item is not present"));
        if (item.getQuantity() < quantity) {
            throw new InventoryNotFoundException("The inventory Item Quantity is less than the Actual Quantity");
        }
        item.setQuantity(item.getQuantity() - quantity);
        inventoryRepository.saveAndFlush(item);
    }
}
