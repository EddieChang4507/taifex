package com.taifex.openapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDto {
	private String startDate;
	private String endDate;
	private String currency;

	@JsonProperty("startDate")
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@JsonProperty("endDate")
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@JsonProperty(" currency ")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
