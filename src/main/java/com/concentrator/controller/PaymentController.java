package com.concentrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.concentrator.config.DatabaseUri;
import com.concentrator.model.RezultatTransakcije;
import com.concentrator.model.Uplata;
import com.concentrator.service.PaypalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/pay")
public class PaymentController {
	
	private final DatabaseUri databaseUri;
	private final RestTemplate restTemplate;
	private final PaypalService paypalService;
	
	@Autowired
	public PaymentController(PaypalService paypalService, DatabaseUri databaseUri, RestTemplate restTemplate) {
		this.databaseUri = databaseUri;
		this.restTemplate = restTemplate;
		this.paypalService = paypalService;
	}
	
	@PostMapping
	@ResponseBody
	public String pay(@RequestBody Uplata uplata) throws PayPalRESTException {
		switch (uplata.getTipUplate().toString()) {
			case "PAYPAL":
				return paypalService.getPaymentLink(uplata);
			case "ACQUIRER":
				return restTemplate.postForObject(databaseUri.getBankUri() + "/api/placanje/generisiPlacanjeLink/"+uplata.getOsiguranje().getId(), uplata, String.class);
			default:
				return "";
		}
	}
	
	@GetMapping("/cancel/{uplataId}")
	public void cancelPayPal(@PathVariable("uplataId")Long uplataId){
		restTemplate.postForObject(databaseUri.getPaymentHandler() + "/payment/cancel", uplataId, Void.class);
	}

	@GetMapping("/success/{uplataId}")
	public void successPayPal(@RequestParam("paymentId")String paymentId,
							 @RequestParam("PayerID")String payerId,
							 @PathVariable("uplataId")Long uplataId){
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")){
				restTemplate.postForObject(databaseUri.getPaymentHandler() + "/payment/success", uplataId, Void.class);
				return;
			}
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		restTemplate.postForObject(databaseUri.getPaymentHandler() + "/payment/error", uplataId, Void.class);
	}
	
	@PostMapping("/successUplata/{uplataId}")
	public void successUplata(@RequestBody RezultatTransakcije rezultatTransakcije, @PathVariable("uplataId")Long uplataId){
		restTemplate.postForObject(databaseUri.getPaymentHandler() + "/payment/success", uplataId, Void.class);
	}
	
	@GetMapping("/cancelUplata/{uplataId}")
	public void cancelUplata(@RequestBody RezultatTransakcije rezultatTransakcije, @PathVariable("uplataId")Long uplataId){
		restTemplate.postForObject(databaseUri.getPaymentHandler() + "/payment/cancel", uplataId, Void.class);
	}
	
}
