package com.concentrator.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.concentrator.config.PaypalConfig;
import com.concentrator.model.Uplata;
import com.concentrator.service.PaypalService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PaypalServiceImpl implements PaypalService {

	@Value("${paypal.success_url}")
	private String successUrl;
	@Value("${paypal.cancel_url}")
	private String cancelUrl;
	
	private final PaypalConfig payPalConfig;
	
	@Autowired
	public PaypalServiceImpl(PaypalConfig payPalConfig) {
		this.payPalConfig = payPalConfig;
	}
	
	@Override
	public String getPaymentLink(Uplata uplata) throws PayPalRESTException {
		Payment payment = this.createPayment(
			uplata.getIznos(), 
			"USD", 
			"paypal", 
			"sale",
			"Insurance sale", 
			cancelUrl + "/" + uplata.getId(), 
			successUrl + "/" + uplata.getId());
		for(Links links : payment.getLinks()){
			if(links.getRel().equals("approval_url")){
				return links.getHref();
			}
		}
		return cancelUrl + "/" + uplata.getId();
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
		
		return payment.create(payPalConfig.apiContext());
	}

	@Override
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);
		return payment.execute(payPalConfig.apiContext(), paymentExecution);
	}

}
