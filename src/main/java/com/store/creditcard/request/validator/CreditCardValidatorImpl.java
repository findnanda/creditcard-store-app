package com.store.creditcard.request.validator;

import java.util.stream.IntStream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class CreditCardValidatorImpl implements ConstraintValidator<CreditCardValidator, String> {
	
	private static Log LOGGER = LogFactory.getLog(CreditCardValidatorImpl.class);
		
	@Override
	public boolean isValid(String cardNumber, ConstraintValidatorContext context) {
		LOGGER.info("validating card number");
		char[] charArray = cardNumber.toCharArray();
		final boolean[] toggle = { false };
		int cardLength = cardNumber.length() - 1;
		return IntStream.range(0, cardNumber.length()).mapToObj(i -> i)
				.map(i -> calculateIndexValue(charArray[cardLength - i], toggle))
				.reduce((val1, val2) -> val1 + val2).filter(total -> total % 10 == 0).isPresent();
	}

	private int calculateIndexValue(char charValue, boolean[] toggle) {
		int val = Character.getNumericValue(charValue);
		int interimVal = (toggle[0] = !toggle[0]) ? val : 2 * val;
		return interimVal > 9 ? interimVal - 9 : interimVal;
	}

}
