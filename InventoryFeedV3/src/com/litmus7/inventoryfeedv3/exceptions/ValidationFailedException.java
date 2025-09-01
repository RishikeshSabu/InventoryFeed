package com.litmus7.inventoryfeedv3.exceptions;

public class ValidationFailedException extends Exception{
	public ValidationFailedException(String errorMessage) {
		super(errorMessage);
	}
}
