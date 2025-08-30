package com.litmus7.inventoryfeedv1.exceptions;

public class InventoryFeedServiceException extends Exception{
	public InventoryFeedServiceException(String errorMessage,Throwable cause) {
		super(errorMessage,cause);
	}
	public InventoryFeedServiceException(String errorMessage) {
		super(errorMessage);
	}
}
