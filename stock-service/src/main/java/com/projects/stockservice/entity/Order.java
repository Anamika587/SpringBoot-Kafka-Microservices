package com.projects.stockservice.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders") // Maps to MongoDB collection
public class Order {

    @Id
    private String id;
    private String orderId;
    private String name;
    private int qty;
    private double price;
    private String customerEmail;

    public Order(String orderId, String name, int qty, double price, String customerEmail) {
        this.orderId = orderId;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.customerEmail = customerEmail;
    }
}

