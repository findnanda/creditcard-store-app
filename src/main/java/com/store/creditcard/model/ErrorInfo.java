package com.store.creditcard.model;

import org.springframework.http.HttpStatus;

public class ErrorInfo {
	
	private String url;
    private String errorMessage;
    private HttpStatus httpStatus;

    public ErrorInfo(){}
    
    public ErrorInfo(String url, Exception ex, String errorMsg, HttpStatus statusCode) {
        this.url = url;
        this.httpStatus = statusCode;
        this.errorMessage = errorMsg != null ? errorMsg:ex.getMessage();
    }

	@Override
	public String toString() {
		return "ErrorInfo [url=" + url + ", ex=" + errorMessage + "]";
	}
	
	public String getUrl() {
		return url;
	}

	public String getEx() {
		return errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}

