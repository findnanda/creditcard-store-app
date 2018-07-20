package com.store.creditcard.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mongodb.MongoWriteException;
import com.mongodb.WriteError;
import com.store.creditcard.model.CreditCard;
import com.store.creditcard.request.CreditCardRequestParam;
import com.store.creditcard.service.CreditCardStoreService;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardControllerTest {

	@Mock
	private CreditCardStoreService creditCardStoreService;

	@InjectMocks
	private CreditCardController creditCardController;

	@Test
	public void testAddCard() {
		Mockito.when(creditCardStoreService.storeCardDetails(Mockito.any(CreditCard.class))).thenReturn("objectId");

		CreditCardRequestParam ccRequestParam = new CreditCardRequestParam("1234567", new BigDecimal(2000.00),
				"mockName");

		ResponseEntity<String> result = creditCardController.add(ccRequestParam);

		ArgumentCaptor<CreditCard> argument = ArgumentCaptor.forClass(CreditCard.class);

		Mockito.verify(creditCardStoreService).storeCardDetails(argument.capture());

		assertThat(new BigDecimal(0.00), Matchers.comparesEqualTo(argument.getValue().getBalance()));
		assertEquals("1234567", argument.getValue().getCreditCardNumber());
		assertTrue(new BigDecimal(2000.00).compareTo(argument.getValue().getCardLimit()) == 0);
		assertEquals("mockName", argument.getValue().getGivenName());
		assertEquals("objectId", result.getBody());
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
	}

	@Test
	public void testCharge() throws ParseException {
		Mockito.doNothing().when(creditCardStoreService).chargeCard(Mockito.any(String.class),
				Mockito.any(String.class), Mockito.any(BigDecimal.class));

		ResponseEntity<String> result = creditCardController.charge("objectId", "mockName", "£200.00");

		ArgumentCaptor<BigDecimal> chargeCaptor = ArgumentCaptor.forClass(BigDecimal.class);
		ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
		
		Mockito.verify(creditCardStoreService).chargeCard(stringCaptor.capture(), stringCaptor.capture(), chargeCaptor.capture());
		assertTrue(new BigDecimal("200.00").compareTo(chargeCaptor.getValue()) == 0);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test(expected = DuplicateKeyException.class)
	public void testAddCreditCardAlreadyExists() {
		
		Mockito.when(creditCardStoreService.storeCardDetails(Mockito.any(CreditCard.class)))
				.thenThrow(new DuplicateKeyException("Card already exists"));

		CreditCardRequestParam ccRequestParam = new CreditCardRequestParam("1234567", new BigDecimal(2000.00),
				"mockName");
		ResponseEntity<String> result = null;

		result = creditCardController.add(ccRequestParam);

		ArgumentCaptor<CreditCard> argument = ArgumentCaptor.forClass(CreditCard.class);

		Mockito.verify(creditCardStoreService).storeCardDetails(argument.capture());
	}
}
