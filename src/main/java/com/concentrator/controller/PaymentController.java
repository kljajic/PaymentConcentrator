package com.concentrator.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.concentrator.model.Uplata;
import com.concentrator.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/pay")
public class PaymentController {

	@Value("${paypal.success_url}")
	private String successUrl;
	@Value("${paypal.cancel_url}")
	private String cancelUrl;
	
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
	public void pay(@RequestBody Uplata uplata, HttpServletResponse response) throws IOException{
		try {
			Payment payment = paypalService.createPayment(
					uplata.getIznos(), 
					"RSD", 
					"paypal", 
					"sale",
					"Insurance sale", 
					cancelUrl + '/' + uplata.getId(), 
					successUrl + '/' + uplata.getId());
			for(Links links : payment.getLinks()){
				if(links.getRel().equals("approval_url")){
					response.sendRedirect(links.getHref());
				}
			}
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/cancel/{uplataId}")
	public void cancelPay(@PathVariable("uplataId")Long uplataId){
		restTemplate.postForObject(databaseUri.getDatabaseUri() + "/payment/cancel", uplataId, Void.class);
	}

	@GetMapping("/success/{uplataId}")
	public void successPay(@RequestParam("paymentId")String paymentId,
							 @RequestParam("PayerID")String payerId,
							 @PathVariable("uplataId")Long uplataId){
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")){
				restTemplate.postForObject(databaseUri.getDatabaseUri() + "/payment/success", uplataId, Void.class);
				return;
			}
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		restTemplate.postForObject(databaseUri.getDatabaseUri() + "/payment/error", uplataId, Void.class);
	}
	
}
