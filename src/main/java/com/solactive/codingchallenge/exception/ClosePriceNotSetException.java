package com.solactive.codingchallenge.exception;

@SuppressWarnings("serial")
public class ClosePriceNotSetException extends Exception{
	public ClosePriceNotSetException(String message) {
		super(message);
	}
}
