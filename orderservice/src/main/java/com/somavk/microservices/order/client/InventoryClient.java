package com.somavk.microservices.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value= "inventory", url = "${gateway.url}")
public interface InventoryClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/inventories/{skuCode}/availability")
    boolean isInStock(@PathVariable String skuCode, @RequestParam int quantity);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/inventories/{skuCode}/reduce")
    String reduceStockInventory(@PathVariable String skuCode, @RequestParam int quantity);
}
