package com.litmus7.inventoryfeedv2.exceptions;

public class InventoryFeedDaoException extends Exception{
	public InventoryFeedDaoException(String errorMessage) {
		super(errorMessage);
	}
	public InventoryFeedDaoException(String errorMessage,Throwable cause) {
		super(errorMessage,cause);
	}
}
