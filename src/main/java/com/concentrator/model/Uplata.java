package com.concentrator.model;

import java.io.Serializable;
import java.util.Date;


public class Uplata implements Serializable{

	private static final long serialVersionUID = -2184014031936616233L;


	private Long id;
	private String trgovacId;
	private String lozinkaTrgovca;
	private double iznos;
	private Date datumUplate;
	private String errorUrl;
	private Osiguranje osiguranje;
	private StatusUplate status;
	private String nacinPlacanja;
	
	public Uplata() {
	}
	
	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}
	


	public String getTrgovacId() {
		return trgovacId;
	}

	public void setTrgovacId(String trgovacId) {
		this.trgovacId = trgovacId;
	}

	public String getLozinkaTrgovca() {
		return lozinkaTrgovca;
	}

	public void setLozinkaTrgovca(String lozinkaTrgovca) {
		this.lozinkaTrgovca = lozinkaTrgovca;
	}

	public double getIznos() {
		return iznos;
	}

	public void setIznos(double iznos) {
		this.iznos = iznos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusUplate getStatus() {
		return status;
	}

	public void setStatus(StatusUplate status) {
		this.status = status;
	}

	public Date getDatumUplate() {
		return datumUplate;
	}

	public void setDatumUplate(Date datumUplate) {
		this.datumUplate = datumUplate;
	}

	public Osiguranje getOsiguranje() {
		return osiguranje;
	}

	public void setOsiguranje(Osiguranje osiguranje) {
		this.osiguranje = osiguranje;
	}

	public String getNacinPlacanja() {
		return nacinPlacanja;
	}

	public void setNacinPlacanja(String nacinPlacanja) {
		this.nacinPlacanja = nacinPlacanja;
	}
	
}
