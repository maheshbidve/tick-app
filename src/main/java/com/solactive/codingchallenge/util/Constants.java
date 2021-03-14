package com.solactive.codingchallenge.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static final String TIMESTAMP = "TIMESTAMP";
	public static final String PRICE = "PRICE";
	public static final String CLOSE_PRICE = "CLOSE_PRICE";
	public static final String CURRENCY = "CURRENCY";
	public static final String RIC = "RIC";
	
	public static final String EQUAL = "=";
	public static final String PIPE = "|";
	public static final String DEFAULT_FILE_PATTERN = "yyyy-MM-dd-HH-mm-ss";
	public static final Map<String, String> API_KEYS = new HashMap<String, String>();
	static {
		API_KEYS.put("ric1", "a1b2c33d4e5f6g7h8i9jakblc");
		API_KEYS.put("ric2", "b2b2c323df5f6g7h8i9jakzad");
		API_KEYS.put("ric3", "c3b2cd34fg3qdf6g7h8i9jadf");
	}
}
