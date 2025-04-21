package com.somavk.microservices.order.controller;


import com.somavk.microservices.order.entity.OrderRequest;
import com.somavk.microservices.order.entity.OrderResponse;
import com.somavk.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderRequest orderRequest) {
//        orderService.createOrder(orderRequest);
        orderService.createOrderWIthCustomClient(orderRequest);
        return "Order Placed Successfully.";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }
}
