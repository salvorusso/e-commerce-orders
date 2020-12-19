package com.unict.dsbd.orders.order;
import com.unict.dsbd.orders.product.Product;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;


public class Order {
        @Id
        private UUID id;
        private double total;
        private List<Product> products;
        private String shippingAddress;
        private String billingAddress;
        private int userId;
        private String extraArgs;

    public Order(UUID id, double total, List<Product> products, String shippingAddress, String billingAddress, int userId, String extraArgs) {
        this.id = id;
        this.total = total;
        this.products = products;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.userId = userId;
        this.extraArgs = extraArgs;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", total:" + total +
                ", products:" + products +
                ", shippingAddress:'" + shippingAddress + '\'' +
                ", billingAddress:'" + billingAddress + '\'' +
                ", userId:" + userId +
                ", extraArgs:'" + extraArgs + '\'' +
                '}';
    }

    public Order setId(UUID id) {
        this.id = id;
        return this;
    }

    public Order setTotal(double total) {
        this.total = total;
        return this;
    }

    public Order setProducts(List<Product> products) {
        this.products = products;
        return this;
    }

    public Order setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public Order setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public Order setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Order setExtraArgs(String extraArgs) {
        this.extraArgs = extraArgs;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public int getUserId() {
        return userId;
    }

    public String getExtraArgs() {
        return extraArgs;
    }
}
