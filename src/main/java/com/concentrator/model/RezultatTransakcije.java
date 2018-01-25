package com.concentrator.model;

import java.util.Date;

public class RezultatTransakcije extends Transakcija {
	
	private String issuerOrderId;
	private String issuerTimestamp;
	private boolean rezultat;
	
	public RezultatTransakcije(){
		
	}
	
	public RezultatTransakcije(String pan, String sigurnosniKod, String nazivVlasnikaKartice, Date datumVazenja,
			String iznos, String acquirerOrderId, String acquirerTimestamp, String acquirerSwiftCode,String issuerOrderId, String issuerTimestamp, boolean rezultat) {
		super(pan, sigurnosniKod, nazivVlasnikaKartice, datumVazenja, iznos, acquirerOrderId, acquirerTimestamp, acquirerSwiftCode);
		this.issuerOrderId = issuerOrderId;
		this.issuerTimestamp = issuerOrderId;
		this.rezultat = rezultat;
	}

	public String getIssuerOrderId() {
		return issuerOrderId;
	}
	public void setIssuerOrderId(String issuerOrderId) {
		this.issuerOrderId = issuerOrderId;
	}
	public String getIssuerTimestamp() {
		return issuerTimestamp;
	}
	public void setIssuerTimestamp(String issuerTimestamp) {
		this.issuerTimestamp = issuerTimestamp;
	}
	public boolean isRezultat() {
		return rezultat;
	}
	public void setRezultat(boolean rezultat) {
		this.rezultat = rezultat;
	}
	
}
