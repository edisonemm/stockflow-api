package com.stockflowapi.controller;

import com.stockflowapi.model.Product;
import com.stockflowapi.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private InventoryService service;

    //POST ADD PRODUCT
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product saved = service.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("❌ " + e.getMessage());
        }
    }

    //GET LIST ALL PRODUCTS
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = service.listAllProducts();
        return ResponseEntity.ok(products);
    }

    //GET SEARCH BY CODE
    @GetMapping("/code/{code}")
    public ResponseEntity<?> getProductByCode(@PathVariable String code) {
        Optional<Product> product = service.searchByCode(code);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Product not found");
    }

    //GET SEARCH BY NAME
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        List<Product> products = service.searchByName(name);
        return ResponseEntity.ok(products);
    }

    //PUT UPDATE STOCK
    @PutMapping("/{code}")
    public ResponseEntity<?> updateQuantity(@PathVariable String code, @RequestParam int quantity) {
        try {
            Product updated = service.updateQuantity(code, quantity);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ " + e.getMessage());

        }
    }

    //DELETE PRODUCT
    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteProduct(@PathVariable String code) {
        try {
            service.deleteProduct(code);
            return ResponseEntity.ok("✅ Product deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ " + e.getMessage());
        }
    }

    // GET LOW STOCK PRODUCTS
    @GetMapping("/alerts/low-stock")
    public ResponseEntity<List<Product>> getLowStockProducts() {
        List<Product> lowStock = service.getLowStockProducts();
        return ResponseEntity.ok(lowStock);
    }

    //GET TOTAL INVENTORY VALUE
    @GetMapping("/report/total-value")
    public ResponseEntity<?> getTotalInventoryValue() {
        double total = service.getTotalInventoryValue();
        return ResponseEntity.ok("Total Inventory Value: $" + String.format("%.2f", total));
    }

    //GET FULL REPORT
    @GetMapping("/report")
    public ResponseEntity<?> generateReport() {
        String report = service.generateReport();
        return ResponseEntity.ok(report);
    }
}
