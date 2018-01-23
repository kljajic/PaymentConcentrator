package com.concentrator.service;

import com.concentrator.model.Uplata;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaypalService {

	String getPaymentLink(Uplata uplata) throws PayPalRESTException;
	
	Payment createPayment(double iznos, 
						  String valuta,
						  String metod,
						  String svrha,
						  String opis,
						  String cancelUrl,
						  String successUrl) throws PayPalRESTException;
	
	Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
	
}
