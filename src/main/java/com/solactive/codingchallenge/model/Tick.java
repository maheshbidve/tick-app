package com.solactive.codingchallenge.model;

import java.util.Date;

public class Tick {
	private Date timestamp;
	private double price;
	private double close_price;
	private String ricName;
	private String currency;
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getClose_price() {
		return close_price;
	}
	public void setClose_price(double close_price) {
		this.close_price = close_price;
	}
	public String getRicName() {
		return ricName;
	}
	public void setRicName(String ricName) {
		this.ricName = ricName;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
    
    
}
