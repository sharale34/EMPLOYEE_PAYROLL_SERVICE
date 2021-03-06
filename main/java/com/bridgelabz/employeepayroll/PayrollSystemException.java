package com.bridgelabz.employeepayroll;

public class PayrollSystemException extends Exception {

	enum ExceptionType {
		UPDATE_FILE_EXCEPTION, RETRIEVE_EXCEPTION, INSERT_EXCEPTION
	}

	ExceptionType type;

	public PayrollSystemException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}
}
