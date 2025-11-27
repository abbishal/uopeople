package com.ecommerce;

public class Product {
    private static int Counter = 1;
    private int productID;
    private String name;
    private double price;

    // Constructor
    public Product(String name, double price) {
        this.productID = Counter++;
        this.name = name;
        this.price = price;
    }

    // Getters and Setters
    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return "Product: " + name +
                ", price=" + price;
    }
}
