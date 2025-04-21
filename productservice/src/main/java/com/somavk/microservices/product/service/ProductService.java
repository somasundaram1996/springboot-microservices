package com.somavk.microservices.product.service;

import com.somavk.microservices.product.entity.ProductRequest;
import com.somavk.microservices.product.entity.ProductResponse;
import com.somavk.microservices.product.exception.ProductNotFoundException;
import com.somavk.microservices.product.mapper.ProductMapper;
import com.somavk.microservices.product.model.Product;
import com.somavk.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product objToSave = ProductMapper.toDto(productRequest);
        Product savedProduct = productRepository.save(objToSave);
        log.info("Product saved to Database");
        return ProductMapper.toResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {
        log.info("Retrieving saved Products from Database");
        return productRepository.findAll().stream().map(ProductMapper::toResponse).toList();
    }

    public ProductResponse getProductById(int id) {
        log.info("Retrieving saved Products from Database for a specific id");
        return ProductMapper.toResponse(productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("The product is not present")));
    }
}
