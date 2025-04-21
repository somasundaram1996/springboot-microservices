package com.somavk.microservices.order.exception;

public class ProductNotInStockException extends RuntimeException {
    public ProductNotInStockException(String message) {
        super(message);
    }
}
