package com.store.creditcard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mongodb.MongoWriteException;
import com.store.creditcard.model.ErrorCodeEnum;
import com.store.creditcard.model.ErrorInfo;
import com.store.creditcard.model.RestError;
import com.store.creditcard.service.exception.CreditCardServiceException;

@ControllerAdvice
class GlobalExceptionHandler {

	private static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);

	@ExceptionHandler(value = { DuplicateKeyException.class, MongoWriteException.class })
	ResponseEntity<ErrorInfo> handleConflict(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		LOGGER.error("Resource already exists");
		ErrorInfo errorInfo = new ErrorInfo(request.getRequestURL().toString(), ex,
				ErrorCodeEnum.DUP_RES_EXISTS.getDefaultMessage(), HttpStatus.CONFLICT);
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { CreditCardServiceException.class })
	ResponseEntity<RestError> handleServiceException(HttpServletRequest request, HttpServletResponse response, CreditCardServiceException ccse) {
		LOGGER.error("Resource not found");
		RestError restError = ccse.transformToRestError();
    	restError.setRequestUrl(request.getRequestURL().toString());
		return new ResponseEntity<RestError>(restError, restError.getHttpStatus());
	}
	
	@ExceptionHandler(value = { ConstraintViolationException.class })
	ResponseEntity<ErrorInfo> handleValidationException(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException cve) {
		LOGGER.error("Validation error", cve);
		ErrorInfo errorInfo = new ErrorInfo(request.getRequestURL().toString(), cve,
				ErrorCodeEnum.INVALID_PARAMS.getDefaultMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}
}
