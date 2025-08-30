package com.litmus7.inventoryfeedv2.exceptions;

public class ValidationFailedException extends Exception{
	public ValidationFailedException(String errorMessage) {
		super(errorMessage);
	}
}
