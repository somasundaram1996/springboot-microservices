package com.somavk.microservices.product.mapper;

import com.somavk.microservices.product.entity.ProductRequest;
import com.somavk.microservices.product.entity.ProductResponse;
import com.somavk.microservices.product.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public static Product toDto(ProductRequest product) {
        return Product.builder()
                .id(product.id())
                .name(product.name())
                .description(product.description())
                .price(product.price())
                .build();
    }
}
