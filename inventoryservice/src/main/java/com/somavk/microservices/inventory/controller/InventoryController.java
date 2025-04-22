package com.somavk.microservices.inventory.controller;


import com.somavk.microservices.inventory.entity.InventoryRequest;
import com.somavk.microservices.inventory.entity.InventoryResponse;
import com.somavk.microservices.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createInventory(@RequestBody List<InventoryRequest> inventoryRequest) {
        log.info("Request Received for Creating Inventory");
        inventoryService.createInventoryEntry(inventoryRequest);
        return "Inventory Created Successfully.";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllInventory() {
        log.info("Request Received for Getting all Inventory Items");
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse getInventoryBySkuCode(@PathVariable String skuCode) {
        log.info("Request Received for Getting Inventory for Item Code {}", skuCode);
        return inventoryService.getInventoryBySkuCode(skuCode);
    }

    @PutMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateInventory(@PathVariable String skuCode, @RequestParam int quantity) {
        log.info("Request Received for Updating Inventory for {}", skuCode);
        inventoryService.updateInventoryBySkuCode(skuCode, quantity);
        return "Inventory Updated Successfully";
    }

    @PutMapping("/{skuCode}/reduce")
    @ResponseStatus(HttpStatus.CREATED)
    public String reduceStockInventory(@PathVariable String skuCode, @RequestParam int quantity) {
        log.info("Request Received for Reducing the Inventory of {} by {}", skuCode, quantity);
        inventoryService.reduceStockInventoryBySkuCode(skuCode, quantity);
        return "Inventory Updated Successfully";
    }

    @GetMapping("/{skuCode}/availability")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String skuCode, @RequestParam int quantity) {
        log.info("Request received for checking Stock for {}", skuCode);
        return inventoryService.checkStockBySkuCode(skuCode, quantity);
    }
}
