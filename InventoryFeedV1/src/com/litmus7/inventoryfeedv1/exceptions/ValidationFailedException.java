package com.litmus7.inventoryfeedv1.exceptions;

public class ValidationFailedException extends Exception{
	public ValidationFailedException(String errorMessage) {
		super(errorMessage);
	}
}
