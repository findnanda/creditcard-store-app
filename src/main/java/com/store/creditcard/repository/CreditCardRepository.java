package com.store.creditcard.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.store.creditcard.model.CreditCard;

public interface CreditCardRepository extends MongoRepository<CreditCard, String> {
	
	CreditCard save(CreditCard creditCard);

}
