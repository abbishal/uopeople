package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private static int Counter = 1;
    private int customerID;
    private String name;
    private List<Product> shoppingCart;

    // Constructor
    public Customer(String name) {
        this.customerID = Counter++;
        this.name = name;
        this.shoppingCart = new ArrayList<>();
    }

    // Getters and Setters
    public int getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getShoppingCart() {
        return shoppingCart;
    }

    // Methods to add and remove products from the shopping cart
    public void addProductToCart(Product product) {
        shoppingCart.add(product);
    }

    public boolean removeProductFromCart(Product product) {
        return shoppingCart.remove(product);
    }

    // Method to calculate total cost
    public double calculateTotalCost() {
        double total = 0;
        for (Product product : shoppingCart) {
            total += product.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Name='" + name + '\'' +
                ", shoppingCart=" + shoppingCart +
                '}';
    }
}
