package com.identityauth.bean;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class RequestProJsonBean {
	
	@JsonProperty("ROWS")
	private Map<String, Object> ROWS;
	
	@JsonProperty("CHECKCODE")
	private String CHECKCODE;

	public Map<String, Object> getROWS() {
		return ROWS;
	}

	public void setROWS(Map<String, Object> rOWS) {
		ROWS = rOWS;
	}

	public String getCHECKCODE() {
		return CHECKCODE;
	}

	public void setCHECKCODE(String cHECKCODE) {
		CHECKCODE = cHECKCODE;
	}

	
}
