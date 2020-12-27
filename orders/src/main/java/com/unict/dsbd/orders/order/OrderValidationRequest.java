package com.unict.dsbd.orders.order;

import java.util.UUID;

public class OrderValidationRequest {
	
	private long timestamp;
	private int status;
	private UUID orderId;
	private String extraArgs;
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public UUID getOrderId() {
		return orderId;
	}
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	public String getExtraArgs() {
		return extraArgs;
	}
	public void setExtraArgs(String extraArgs) {
		this.extraArgs = extraArgs;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderValidationRequest [timestamp=");
		builder.append(timestamp);
		builder.append(", status=");
		builder.append(status);
		builder.append(", orderId=");
		builder.append(orderId);
		builder.append(", extraArgs=");
		builder.append(extraArgs);
		builder.append("]");
		return builder.toString();
	}
	
}
