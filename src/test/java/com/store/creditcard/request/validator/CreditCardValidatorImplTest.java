package com.store.creditcard.request.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardValidatorImplTest {
	
	private CreditCardValidatorImpl creditCardValidatorImpl;
	
	@Before
	public void setUp(){
		creditCardValidatorImpl = new CreditCardValidatorImpl();
	}

	@Test
	public void testIsValidCardSuccess() {
		assertTrue(creditCardValidatorImpl.isValid("79927398713", null));
	}
	
	@Test
	public void testIsValidCardInvalid() {
		assertFalse(creditCardValidatorImpl.isValid("12345678", null));
	}
	
	@Test
	public void testIsValidCardNumberBlank() {
		assertFalse(creditCardValidatorImpl.isValid(" ", null));
	}
	
	@Test
	public void testIsValidCardNumberWithDecimal() {
		assertFalse(creditCardValidatorImpl.isValid("1122334455.66", null));
	}
	
	@Test
	public void testIsValidCardNumberAmex() {
		assertTrue(creditCardValidatorImpl.isValid("375967551646851", null));
	}
	
	@Test
	public void testIsValidCardNumberInsta() {
		assertTrue(creditCardValidatorImpl.isValid("6375713740889755", null));
	}
}
