package com.taifex.openapi.dto;

import java.util.List;

public class ResponseDto {
	private ErrorDto error;
	private List<CurrencyDto> currency;

	public ErrorDto getError() {
		return error;
	}

	public void setError(ErrorDto error) {
		this.error = error;
	}

	public List<CurrencyDto> getCurrency() {
		return currency;
	}

	public void setCurrency(List<CurrencyDto> currency) {
		this.currency = currency;
	}
}
