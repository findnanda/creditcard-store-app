package com.store.creditcard.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.creditcard.request.CreditCardRequestParam;
import com.store.creditcard.service.CreditCardStoreService;

@RestController
@Validated
public class CreditCardController {

	private static Log LOGGER = LogFactory.getLog(CreditCardController.class);

	@Autowired
	private CreditCardStoreService ccStoreService;

	@RequestMapping(value = "/store/creditcard/add", method = RequestMethod.POST)
	public ResponseEntity<String> add(@Valid @RequestBody CreditCardRequestParam ccRequestParam) {
		LOGGER.info("add new card");
		String id = ccStoreService.storeCardDetails(ccRequestParam.mapRepositoryObject());
		return new ResponseEntity<String>(id, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/store/creditcard/{id}/charge", method = RequestMethod.PUT)
	public ResponseEntity<String> charge(@PathVariable String id,
			@Valid @Pattern(regexp = "^[A-Za-z]+$") @RequestParam String name,
			@Valid @Pattern(regexp = "^(£[0-9]+)(.[0-9]{2})+$") @RequestParam String amount) throws ParseException {
		LOGGER.info("charge card");
		ccStoreService.chargeCard(id, name, new BigDecimal(amount.substring(1)));
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
}
