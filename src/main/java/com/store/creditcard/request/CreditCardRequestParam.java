package com.store.creditcard.request;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.creditcard.model.CreditCard;
import com.store.creditcard.request.validator.CreditCardValidator;

public class CreditCardRequestParam {
	
	@JsonProperty("card_number")
	@CreditCardValidator
	@Pattern(regexp = "^[0-9]{1,19}+$")
	private String creditCardNumber;
	@JsonProperty("card_limit")
	private BigDecimal limit;
	@JsonProperty("first_name")
	@NotNull
	@Pattern(regexp = "^[A-Za-z]+$")
	private String name;
	
	public CreditCardRequestParam(String creditCardNumber, BigDecimal limit, String name){
		this.creditCardNumber = creditCardNumber;
		this.limit = limit;
		this.name = name;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public BigDecimal getLimit() {
		return limit;
	}

	public String getName() {
		return name;
	}
	
	public CreditCard mapRepositoryObject(){
		CreditCard creditCard = new CreditCard();
		creditCard.setBalance(new BigDecimal(0.0));
		creditCard.setGivenName(this.name);
		creditCard.setCardLimit(this.limit);
		creditCard.setCreditCardNumber(this.creditCardNumber);
		return creditCard;
	}
}
