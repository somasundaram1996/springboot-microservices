package com.somavk.microservices.product.repository;

import com.somavk.microservices.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
