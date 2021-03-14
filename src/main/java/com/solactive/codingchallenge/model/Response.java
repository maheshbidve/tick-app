package com.solactive.codingchallenge.model;

import org.springframework.http.HttpStatus;

/**
 * This is a POJO for storing Response
 * 
 * @author mahesh.bidve
 *
 */
public class Response {
    private HttpStatus statusCode;
    private String statusMessage;
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
    
    
}
