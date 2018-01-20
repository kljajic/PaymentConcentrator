package com.concentrator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Osiguranje implements Serializable{
	
	private static final long serialVersionUID = -7663607432545399871L;


	private Long id;
	
	private Date datumSklapanja;
	
	private double iznos;
	
	private List<Uplata> uplate;
	
	public Osiguranje() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDatumSklapanja() {
		return datumSklapanja;
	}

	public void setDatumSklapanja(Date datumSklapanja) {
		this.datumSklapanja = datumSklapanja;
	}

	@JsonIgnore
	public List<Uplata> getUplate() {
		if(uplate == null) {
			return new ArrayList<>();
		}
		return uplate;
	}

	@JsonProperty
	public void setUplate(List<Uplata> uplate) {
		this.uplate = uplate;
	}

	public double getIznos() {
		return iznos;
	}

	public void setIznos(double iznos) {
		this.iznos = iznos;
	}
	
}
