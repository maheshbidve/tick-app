package com.solactive.codingchallenge.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

/**
 * This class is used for exception handling
 * 
 * @author mahesh.bidve
 *
 */
@ControllerAdvice
public class ExceptionHelper {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

	/**
	 * handleUnauthorizedException
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = { Unauthorized.class })
	public ResponseEntity<Object> handleUnauthorizedException(Unauthorized ex) {
		logger.error("Unauthorized Exception: ", ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	/**
	 * handleValidationException
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = { IllegalArgumentException.class })
	public ResponseEntity<Object> handleValidationException(IllegalArgumentException ex) {
		logger.error("Validation Exception: ", ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * handleException
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleException(Exception ex) {
		logger.error("Exception: ", ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}