package com.store.creditcard.model;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CreditCard {

	@Id
	public ObjectId _id;
	@Indexed(unique = true)
	private String creditCardNumber;
	private BigDecimal cardLimit;
	private String givenName;
	private BigDecimal balance;
	private BigDecimal charges;
	private BigDecimal credit;
	
	
	public CreditCard() {}
	
	public String get_id() {
		return _id != null?_id.toHexString():null;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public BigDecimal getCardLimit() {
		return cardLimit;
	}
	public void setCardLimit(BigDecimal cardLimit) {
		this.cardLimit = cardLimit;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getCharges() {
		return charges;
	}
	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}
	public BigDecimal getCredit() {
		return credit;
	}
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
} 
