package com.concentrator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUri {
	
	@Value("${spring.data.paypalhandler}")
	private String databaseUri;

	public String getDatabaseUri() {
		return databaseUri;
	}

	public void setDatabaseUri(String databaseUri) {
		this.databaseUri = databaseUri;
	}
}
