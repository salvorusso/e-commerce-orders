package com.unict.dsbd.orders.order;

import java.util.UUID;

public class OrderPaymentRequest {

	private UUID orderId;
	private int userId;
	private double amountPaid;
	private ExtraArgs extraArgs;
	
	public UUID getOrderId() {
		return orderId;
	}
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public ExtraArgs getExtraArgs() {
		return extraArgs;
	}
	public void setExtraArgs(ExtraArgs extraArgs) {
		this.extraArgs = extraArgs;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderPaymentRequest [orderId=");
		builder.append(orderId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", amountPaid=");
		builder.append(amountPaid);
		builder.append(", extraArgs=");
		builder.append(extraArgs);
		builder.append("]");
		return builder.toString();
	}
	
	
}
