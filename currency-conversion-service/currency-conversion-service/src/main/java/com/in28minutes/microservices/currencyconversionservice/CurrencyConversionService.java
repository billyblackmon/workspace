package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class CurrencyConversionService {

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from,
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		
	
		return new CurrencyConversionBean(1L, from, to, BigDecimal.ONE, quantity, quantity,0);
		
	}	// end convertCurrecy()
	
	
}	// end class
