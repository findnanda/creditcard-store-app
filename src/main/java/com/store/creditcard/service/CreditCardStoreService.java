package com.store.creditcard.service;

import java.math.BigDecimal;

import org.bson.types.ObjectId;

import com.store.creditcard.model.CreditCard;

public interface CreditCardStoreService {

	String storeCardDetails(CreditCard creditCard);

	void chargeCard(String id, String name, BigDecimal amount);

}
