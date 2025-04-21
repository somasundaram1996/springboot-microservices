package com.somavk.microservices.order.service;

import com.somavk.microservices.order.client.CustomInventoryClient;
import com.somavk.microservices.order.client.InventoryClient;
import com.somavk.microservices.order.entity.OrderRequest;
import com.somavk.microservices.order.entity.OrderResponse;
import com.somavk.microservices.order.exception.OrderNotFoundException;
import com.somavk.microservices.order.exception.ProductNotInStockException;
import com.somavk.microservices.order.mapper.OrderMapper;
import com.somavk.microservices.order.model.Order;
import com.somavk.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final InventoryClient inventoryClient;

    private final CustomInventoryClient customInventoryClient;

    public void createOrder(OrderRequest orderRequest) {
        boolean isInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (isInStock) {
            inventoryClient.reduceStockInventory(orderRequest.skuCode(), orderRequest.quantity());
            Order objToSave = OrderMapper.toDto(orderRequest);
            Order savedOrder = orderRepository.save(objToSave);
            log.info("Order saved to Database");
        } else {
            throw new ProductNotInStockException("Product with Skucode " + orderRequest.skuCode() + " Not in Stock");
        }
    }

    public void createOrderWIthCustomClient(OrderRequest orderRequest) {
        Mono<Boolean> response = customInventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        Boolean isInStock = response.doOnError(error -> {
            log.error("Error while Calling Inventory for Checking Stock {}", error.getMessage());
        }).map(responses -> responses)
                .onErrorResume(error -> {
                    throw new ProductNotInStockException("Unable to Fetch the Stock of the Order");
                })
                .block();
        if (Boolean.TRUE.equals(isInStock)) {
            customInventoryClient.reduceStockInventory(orderRequest.skuCode(), orderRequest.quantity());
            Order objToSave = OrderMapper.toDto(orderRequest);
            Order savedOrder = orderRepository.save(objToSave);
            log.info("Order saved to Database");
        } else {
            throw new ProductNotInStockException("Product with Skucode " + orderRequest.skuCode() + " Not in Stock");
        }
    }

    public List<OrderResponse> getAllOrders() {
        log.info("Retrieving saved Orders from Database");
        return orderRepository.findAll().stream().map(OrderMapper::toResponse).toList();
    }

    public OrderResponse getOrderById(int id) {
        log.info("Retrieving saved Order from Database for a specific id");
        return OrderMapper.toResponse(orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("The order is not present")));
    }
}
