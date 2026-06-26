package com.stockflowapi.repository;

import com.stockflowapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByCode(String code);
    List<Product> findByNameContainingIgnoreCase(String name);
}
