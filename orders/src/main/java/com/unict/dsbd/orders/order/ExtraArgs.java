package com.unict.dsbd.orders.order;

public class ExtraArgs {
	
	private String error;
	
	public ExtraArgs(String error) {
		this.error = error;
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
		builder.append("extraArgs [error=");
		builder.append(error);
		builder.append("]");
		return builder.toString();
	}
	
	

}
