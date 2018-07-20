package com.store.creditcard.model;

import org.springframework.http.HttpStatus;

public class RestError {

	private HttpStatus httpStatus;
	private String errorCode;
	private String message;
	private String requestUrl;

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Override
	public String toString() {
		return "RestError [httpStatus=" + httpStatus + ", errorCode=" + errorCode
				+ ", message=" + message + ", requestUrl=" + requestUrl + "]";
	}
}
