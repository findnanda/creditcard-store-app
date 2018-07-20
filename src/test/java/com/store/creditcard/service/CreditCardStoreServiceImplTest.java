package com.store.creditcard.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.store.creditcard.model.CreditCard;
import com.store.creditcard.repository.CreditCardRepository;
import com.store.creditcard.service.exception.CreditCardServiceException;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardStoreServiceImplTest {

	@Mock
	private CreditCardRepository creditCardRepository;

	@InjectMocks
	private CreditCardStoreServiceImpl creditCardStoreServiceImpl;
	
	@Test
	public void testAddCard() {
		CreditCard param = createCreditCard(null,null,null, new BigDecimal(500.00));
		Mockito.when(creditCardRepository.insert(Mockito.any(CreditCard.class))).thenReturn(createCreditCard(new ObjectId("5b5104d3c9ad3744284853a7"), null, null, null));
		String id = creditCardStoreServiceImpl.storeCardDetails(param);
		ArgumentCaptor<CreditCard> argument = ArgumentCaptor.forClass(CreditCard.class);
		Mockito.verify(creditCardRepository).insert(argument.capture());
		assertNull(argument.getValue().get_id());
		assertEquals("5b5104d3c9ad3744284853a7", id);
	}
	
	@Test
	public void testChargeCard() {
		CreditCard storedCreditCard = createCreditCard(new ObjectId("5b5104d3c9ad3744284853a7"), new BigDecimal(200.00), new BigDecimal(0.00), new BigDecimal(250.00));
		Mockito.when(creditCardRepository.findById("5b5104d3c9ad3744284853a7")).thenReturn(Optional.ofNullable(storedCreditCard));
		Mockito.when(creditCardRepository.save(Mockito.any(CreditCard.class))).thenReturn(Mockito.any());
				
		creditCardStoreServiceImpl.chargeCard("5b5104d3c9ad3744284853a7", "mockName", new BigDecimal(200.00));
		
		ArgumentCaptor<CreditCard> argument = ArgumentCaptor.forClass(CreditCard.class);
		Mockito.verify(creditCardRepository).save(argument.capture());
		
		assertEquals("5b5104d3c9ad3744284853a7", argument.getValue().get_id());
		assertThat(new BigDecimal(200.00), Matchers.comparesEqualTo(argument.getValue().getBalance()));
	}
	
	@Test
	public void testChargeCardExceedsCardLimit() {
		CreditCard storedCreditCard = createCreditCard(new ObjectId("5b5104d3c9ad3744284853a7"), null, new BigDecimal(400.00), new BigDecimal(500.00));
		Mockito.when(creditCardRepository.findById("5b5104d3c9ad3744284853a7")).thenReturn(Optional.ofNullable(storedCreditCard));
		Mockito.when(creditCardRepository.save(Mockito.any(CreditCard.class))).thenReturn(Mockito.any());
				
		creditCardStoreServiceImpl.chargeCard("5b5104d3c9ad3744284853a7", "mockName", new BigDecimal(200.00));
		
		Mockito.verify(creditCardRepository).findById("5b5104d3c9ad3744284853a7");
		ArgumentCaptor<CreditCard> argument = ArgumentCaptor.forClass(CreditCard.class);
		Mockito.verify(creditCardRepository).save(argument.capture());
		
		assertEquals("5b5104d3c9ad3744284853a7", argument.getValue().get_id());
		assertThat(new BigDecimal(400.00), Matchers.comparesEqualTo(argument.getValue().getBalance()));
	}
	
	@Test(expected=CreditCardServiceException.class)
	public void testChargeCardNotFound() {
		Mockito.when(creditCardRepository.findById("5b5104d3c9ad3744284853a7")).thenReturn(Optional.ofNullable(null));
				
		creditCardStoreServiceImpl.chargeCard("5b5104d3c9ad3744284853a7", "mockName", new BigDecimal(200.00));
		
		Mockito.verify(creditCardRepository).findById("5b5104d3c9ad3744284853a7");
		Mockito.verify(creditCardRepository, Mockito.times(0)).save(Mockito.any(CreditCard.class));
	}
	
	private CreditCard createCreditCard(ObjectId id, BigDecimal charge, BigDecimal balance, BigDecimal cardLimit) {
		CreditCard creditCard = new CreditCard();
		creditCard.set_id(id);
		creditCard.setGivenName("mockName");
		creditCard.setCharges(charge);
		creditCard.setBalance(balance);
		creditCard.setCardLimit(cardLimit);
		return creditCard;
	}
}
