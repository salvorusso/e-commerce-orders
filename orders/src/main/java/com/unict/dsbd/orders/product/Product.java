package com.unict.dsbd.orders.product;

import org.springframework.data.annotation.Id;

public class Product {
    @Id
    public int product_id;
    public int quantity;

    public Product() {
    }

    @Override
    public String toString() {
        return "{" +
                "product_id:" + product_id +
                ", quantity:" + quantity +
                '}';
    }

    public Product(int product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public Product setProduct_id(int product_id) {
        this.product_id = product_id;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
}
