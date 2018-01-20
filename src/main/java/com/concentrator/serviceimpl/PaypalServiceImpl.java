package com.concentrator.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concentrator.service.PaypalService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PaypalServiceImpl implements PaypalService {

	private final APIContext apiContext;
	
	@Autowired
	public PaypalServiceImpl(APIContext apiContext) {
		this.apiContext = apiContext;
	}
	
	@Override
	public Payment createPayment(double iznos, String valuta, String metod, String svrha,
								 String opis, String cancelUrl, String successUrl) throws PayPalRESTException {
		Amount amount = new Amount(valuta, String.format("%.2f", iznos));
		
		Transaction transaction = new Transaction();
		transaction.setDescription(opis);
		transaction.setAmount(amount);
		
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		
		Payer payer = new Payer();
		payer.setPaymentMethod(metod);
		
		Payment payment = new Payment(svrha, payer);
		payment.setTransactions(transactions);
		
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);
		
		return payment.create(apiContext);
	}

	@Override
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(paymentId);
		return payment.execute(apiContext, paymentExecution);
	}

}
