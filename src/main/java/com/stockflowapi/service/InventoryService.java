package com.stockflowapi.service;

import com.stockflowapi.model.Product;
import com.stockflowapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private ProductRepository repository;

    public Product addProduct(Product product) {
        if (repository.findByCode(product.getCode()).isPresent()) {
            throw new RuntimeException("Product with code " + product.getCode() + " already exists.");
        }
        return repository.save(product);
    };

    public List<Product> listAllProducts() {
        return repository.findAll();
    };

    public Optional<Product> searchByCode(String code) {
        return repository.findByCode(code);
    };

    public List<Product> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    };

    public Product updateQuantity(String code, int newQuantity) {
        Optional<Product> product = repository.findByCode(code);
        if (product.isPresent()){
            Product p = product.get();
            p.setQuantity(newQuantity);
            return repository.save(p);
        }
        throw new RuntimeException("Product not found.");
    };

    public void deleteProduct(String code) {
        Optional<Product> product = repository.findByCode(code);
        if (product.isPresent()) {
            repository.delete(product.get());
        } else {
            throw new RuntimeException("Product not found.");
        }
    };

    public List<Product> getLowStockProducts() {
        return repository.findAll().stream()
                .filter(Product::isLowStock).toList();
    };

    public double getTotalInventoryValue() {
        return repository.findAll().stream()
                .mapToDouble(Product::getTotalValue).sum();
    };

    public String generateReport() {
        List<Product> products = repository.findAll();
        StringBuilder report = new StringBuilder();
        report.append("\n=========== INVENTORY REPORT ===================\n");
        report.append("Total Products: ").append(products.size()).append("\n");
        report.append("Total inventory value: $").append(String.format("%.2f", getTotalInventoryValue())).append("\n");
        report.append("Products with Low Stock: ").append(getLowStockProducts().size()).append("\n\n");

        for (Product p : products) {
            report.append(p.toString()).append("\n");
        }
        report.append("=============================================");
        return report.toString();
    };


}
