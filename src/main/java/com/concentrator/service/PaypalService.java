package com.concentrator.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaypalService {

	Payment createPayment(double iznos, 
						  String valuta,
						  String metod,
						  String svrha,
						  String opis,
						  String cancelUrl,
						  String successUrl) throws PayPalRESTException;
	
	Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
	
}
