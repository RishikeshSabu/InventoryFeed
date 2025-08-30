package com.litmus7.inventoryfeedv2.dto;

public class Response<T> {
	private T data;
	private String errorMessage;
	private int statusCode;
	public Response(int statusCode,String errorMessage) {
		this.statusCode=statusCode;
		this.errorMessage=errorMessage;
	}
	public Response(int statusCode,T data) {
		this.statusCode=statusCode;
		this.data=data;
	}
	public T getData() {
		return data;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
}
