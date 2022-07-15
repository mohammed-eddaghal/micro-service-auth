package com.mohammed.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessResourceExceptionDTO {
	 private String errorCode;
	    private String errorMessage;
		private String requestURL;
		private HttpStatus status;
}

