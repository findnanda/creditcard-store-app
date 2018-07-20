package com.store.creditcard.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum ErrorCodeEnum implements ErrorCode {
	
	UNKNOWN_ERROR(1, "UNKNOWN_ERROR"),
	INVALID_PARAMS(2, "INVALID_PARAMS"),
	SERVICE_ERROR(3, "SERVICE_ERROR"),
	DB_DATA_NOT_FOUND(4, "RESOURCE_NOT_FOUND"),
	DUP_RES_EXISTS(5, "DUPLICATE_RESOURCE"),
	
	;
	
	private static final Map<Integer,ErrorCodeEnum> LOOK_UP = new HashMap<Integer,ErrorCodeEnum>();
	static {
		for(ErrorCodeEnum e : EnumSet.allOf(ErrorCodeEnum.class))
			LOOK_UP.put(e.getErrorCode(), e);
	}
	
	private int errorCode;
	private String name;
	
	ErrorCodeEnum(int errorCode, String name) {
		this.errorCode = errorCode;
		this.name = name;
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}
	
	public String getName() {
		return this.name;
	}

	public String getDefaultMessage() {
		switch (this){
		case UNKNOWN_ERROR:
			return "An unknown error has been encountered";
		case INVALID_PARAMS:
			return "Invalid parameters were received";
		case DB_DATA_NOT_FOUND:
			return "Requested resouce not found";
		case DUP_RES_EXISTS:
			return "Resource already exist";
		case SERVICE_ERROR:
			return "Service Exception";
		default: 
			return "An undefined error has been encountered";
		}
	}
	
	public static Optional<ErrorCodeEnum> get(int errorCode) { 
		return EnumSet.allOf(ErrorCodeEnum.class).stream().filter(e -> e.getErrorCode() == errorCode).findAny();
	}


}


