package com.taifex.openapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyDto {
	private String date;

	@JsonProperty("usd")
	private String usdValue;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUsdValue() {
		return usdValue;
	}

	public void setUsdValue(String usdValue) {
		this.usdValue = usdValue;
	}
}
