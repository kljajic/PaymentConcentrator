package com.concentrator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUri {
	
	@Value("${spring.data.paymenthandler}")
	private String paymentHandler;
	
	@Value("${spring.data.bank}")
	private String bankUri;

	public String getPaymentHandler() {
		return paymentHandler;
	}

	public void setPaymentHandler(String paymentHandler) {
		this.paymentHandler = paymentHandler;
	}

	public String getBankUri() {
		return bankUri;
	}

	public void setBankUri(String bankUri) {
		this.bankUri = bankUri;
	}
	
}
