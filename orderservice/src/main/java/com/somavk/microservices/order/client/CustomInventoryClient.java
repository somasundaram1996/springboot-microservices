package com.somavk.microservices.order.client;

import com.somavk.microservices.webclient.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomInventoryClient {
    private final WebClientService customRestClient;
    public Mono<Boolean> isInStock(String skuCode, int quantity) {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("quantity", String.valueOf(quantity));
        Map<String, String> pathParams  = new HashMap<>();
        pathParams.put("skuCode", skuCode);
        log.info("Calling Inventory Service to Check the Product {} is in Stock", skuCode);
        return customRestClient.get("/api/v1/inventories/{skuCode}/availability"
                ,pathParams,queryParameters, Boolean.class);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "")
    public Mono<String> reduceStockInventory(@PathVariable String skuCode, @RequestParam int quantity) {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("quantity", String.valueOf(quantity));
        Map<String, String> pathParams  = new HashMap<>();
        pathParams.put("skuCode", skuCode);
        log.info("Calling Inventory Service to reduce the Stock of {} by {}", skuCode, quantity);
        return customRestClient.get("/api/v1/inventories/{skuCode}/reduce"
                ,pathParams,queryParameters, String.class);
    }
}
