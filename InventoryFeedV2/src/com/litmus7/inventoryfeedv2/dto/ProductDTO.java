package com.litmus7.inventoryfeedv2.dto;

public class ProductDTO {
	private int productId;
	private String productName;
	private int quantity;
	private double price;
	
	public ProductDTO(int productId,String productName,int quantity,double price) {
		this.productId=productId;
		this.productName=productName;
		this.quantity=quantity;
		this.price=price;
	}
	public int getProductId() {
		return productId;
	}
	public String getProductName() {
		return productName;
	}
	
	public double getPrice() {
		return price;
	}
	public int getQuantity() {
		return quantity;
	}
	
	
	
}
