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
        private String status;
        private ExtraArgs extraArgs;

    public Order(UUID id, double total, List<Product> products, String shippingAddress, String billingAddress, int userId, ExtraArgs extraArgs) {
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

    public Order setExtraArgs(ExtraArgs extraArgs) {
        this.extraArgs = extraArgs;
        return this;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

    public ExtraArgs getExtraArgs() {
        return extraArgs;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [id=");
		builder.append(id);
		builder.append(", total=");
		builder.append(total);
		builder.append(", products=");
		builder.append(products);
		builder.append(", shippingAddress=");
		builder.append(shippingAddress);
		builder.append(", billingAddress=");
		builder.append(billingAddress);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", status=");
		builder.append(status);
		builder.append(", extraArgs=");
		builder.append(extraArgs);
		builder.append("]");
		return builder.toString();
	}
    
    
}
