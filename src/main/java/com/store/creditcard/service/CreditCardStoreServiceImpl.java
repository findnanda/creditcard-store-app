package com.store.creditcard.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.store.creditcard.model.CreditCard;
import com.store.creditcard.model.ErrorCodeEnum;
import com.store.creditcard.repository.CreditCardRepository;
import com.store.creditcard.service.exception.CreditCardServiceException;

@Component
public class CreditCardStoreServiceImpl implements CreditCardStoreService {

	@Autowired
	private CreditCardRepository creditCardRepository;

	@Override
	public String storeCardDetails(final CreditCard creditCard) {
		return creditCard != null ? creditCardRepository.insert(creditCard).get_id():null;
	}

	@Override
	public void chargeCard(String id, String name, BigDecimal charge) {
		Optional<CreditCard> creditCard = findById(id);
		if (creditCard.isPresent()) {
			BigDecimal balance = calculateBalance(creditCard.get(), charge);
			creditCard.get().setBalance(balance);
			creditCard.get().setCharges(charge);
			creditCardRepository.save(creditCard.get());
		} else {
			throw new CreditCardServiceException("Card not found", ErrorCodeEnum.DB_DATA_NOT_FOUND);
		}

	}

	private BigDecimal calculateBalance(final CreditCard creditCard, BigDecimal charge) {
		final BigDecimal newBalance = creditCard.getBalance().add(charge);
		return newBalance.compareTo(creditCard.getCardLimit()) == 1 ? creditCard.getBalance() : newBalance;
	}
	
//	private boolean checkBalance(final CreditCard creditCard, BigDecimal newBalance) {
//		return newBalance.compareTo(creditCard.getBalance()) == 0;
//	}
	
	private Optional<CreditCard> findById(String id){
		return StringUtils.isNotEmpty(id) ? creditCardRepository.findById(id):Optional.empty();
	}
}
