package com.stockflowapi.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int minimumStock;

    public Product() {}

    public Product(String name, String code, int quantity, double price, int minimumStock) {
        this.name = name;
        this.code = code;
        this.quantity = quantity;
        this.price = price;
        this.minimumStock = minimumStock;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public int getMinimumStock() { return minimumStock; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public boolean isLowStock() {
        return quantity < minimumStock;
    }

    public double getTotalValue() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Code: %s) | Stock: %d | Price: $%.2f | Total: $%.2f %s",
                id.substring(0, 8),
                name,
                code,
                quantity,
                price,
                getTotalValue(),
                isLowStock() ? "⚠️ LOW STOCK" : "");
    }
}
