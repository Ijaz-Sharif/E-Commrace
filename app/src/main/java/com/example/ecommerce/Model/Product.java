package com.example.ecommerce.Model;

public class Product {

    String name,price,description;
    int image,productId,quantity;



    public Product(String name, String price, String description, int image, int productId, int quantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Product(String name, String price, String description, int image, int productId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
