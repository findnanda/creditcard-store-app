package com.store.creditcard.model;

import org.springframework.http.HttpStatus;

public class ErrorCodeMapping {
	
	ErrorCodeMapping(){}

	public static HttpStatus getHttpStatusCode(ErrorCode errorCodeEnum){
		HttpStatus httpStatus = null;
		switch(errorCodeEnum.getErrorCode()){
		case 1:
		case 3:
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			break;
		case 2:
			httpStatus = HttpStatus.BAD_REQUEST;
			break;
		case 4:
			httpStatus = HttpStatus.NOT_FOUND;
			break;
		case 5:
			httpStatus = HttpStatus.CONFLICT;
			break;
		}
		
		return httpStatus;
	}
	 
	
}