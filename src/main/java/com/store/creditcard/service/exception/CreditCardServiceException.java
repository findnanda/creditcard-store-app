package com.store.creditcard.service.exception;

import org.springframework.http.HttpStatus;

import com.store.creditcard.model.ErrorCode;
import com.store.creditcard.model.ErrorCodeEnum;
import com.store.creditcard.model.ErrorCodeMapping;
import com.store.creditcard.model.RestError;

public class CreditCardServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	protected ErrorCode errorCode;
	
	public CreditCardServiceException(String message, ErrorCodeEnum errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public RestError transformToRestError() {
		RestError restError = new RestError();
		restError.setHttpStatus(getHttpStatus());
		restError.setErrorCode(String.valueOf(errorCode.getErrorCode()));
		restError.setMessage(super.getMessage());
		return restError;
	}
	
	public HttpStatus getHttpStatus() {
		return ErrorCodeMapping.getHttpStatusCode(errorCode);
	}
}

