package com.pluralsight;

public class Product {
    private String sku;
    private String name;
    private double price;

    public Product(String sku, String name, double price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    @Override
    public String toString() {
        return "Product:" + "sku='" + sku + '\'' + "| name:" + name + '\'' + "| price= $" + price;
    }
}
