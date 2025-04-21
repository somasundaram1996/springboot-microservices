package com.somavk.microservices.inventory.repository;

import com.somavk.microservices.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Optional<Inventory> findBySkuCode(String skuCode);
}
