package com.unict.dsbd.orders.order;

import org.springframework.beans.factory.annotation.Value;

public class CustomErrorMessage {
	
	private long timestamp;
	private String sourceIp;
	private String service;
	private String request;
	private String error;

	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getSourceIp() {
		return sourceIp;
	}
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomErrorMessage [timestamp=");
		builder.append(timestamp);
		builder.append(", sourceIp=");
		builder.append(sourceIp);
		builder.append(", service=");
		builder.append(service);
		builder.append(", request=");
		builder.append(request);
		builder.append(", error=");
		builder.append(error);
		builder.append("]");
		return builder.toString();
	}
	
	

}
