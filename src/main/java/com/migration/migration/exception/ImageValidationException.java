package com.migration.migration.exception;

import lombok.AllArgsConstructor;


public class ImageValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5659889578448717144L;
    private String message;
	
    public ImageValidationException(String message) {
		super();
		this.message = message;
	}


}
