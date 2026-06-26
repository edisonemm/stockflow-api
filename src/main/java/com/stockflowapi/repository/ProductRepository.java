package com.stockflowapi.repository;

import com.stockflowapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByCode(String code);
    List<Product> findByNameContainingIgnoreCase(String name);
}
