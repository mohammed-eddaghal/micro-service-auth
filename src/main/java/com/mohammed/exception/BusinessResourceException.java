package com.mohammed.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class BusinessResourceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7625754983250836825L;

    private Long resourceId;
    private String errorCode;
    private HttpStatus status;

    public BusinessResourceException(String message) {
        super(message);
    }
    
    public BusinessResourceException(Long resourceId, String message) {
        super(message);
        this.resourceId = resourceId;
    }
    public BusinessResourceException(Long resourceId, String errorCode, String message) {
        super(message);
        this.resourceId = resourceId;
        this.errorCode = errorCode;
    }
    
    public BusinessResourceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public BusinessResourceException(String errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

}

