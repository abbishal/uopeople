// File: com/ecommerce/orders/Order.java
package com.ecommerce.orders;

import com.ecommerce.Customer;
import com.ecommerce.Product;
import java.util.List;

public class Order {
    private static int Counter = 1;
    private int orderID;
    private Customer customer;
    private List<Product> products;
    private double orderTotal;
    private String status;

    // Constructor
    public Order(Customer customer, List<Product> products) {
        this.orderID = Counter++;
        this.customer = customer;
        this.products = products;
        this.status = "Processing";
        this.orderTotal = calculateOrderTotal();
        System.out.println("Order successfully created.");
    }

    // Getters and Setters
    public int getOrderID() {
        return orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    // Method to calculate order total
    private double calculateOrderTotal() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return "customer=" + customer +
                ", products=" + products +
                ", orderTotal=" + orderTotal;
    }
}
