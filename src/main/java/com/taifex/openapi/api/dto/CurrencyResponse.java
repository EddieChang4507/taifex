package com.taifex.openapi.api.dto;

import java.util.List;
import java.util.Objects;

public class CurrencyResponse {
	private ErrorResponse error;
	private List<CurrencyData> currency;

	// Getters and Setters
	public ErrorResponse getError() {
		return error;
	}

	public void setError(ErrorResponse error) {
		this.error = error;
	}

	public List<CurrencyData> getCurrency() {
		return currency;
	}

	public void setCurrency(List<CurrencyData> currency) {
		this.currency = currency;
	}

	public static class ErrorResponse {
		private String code;
		private String message;

		// Getters and Setters
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	public static class CurrencyData {
		private String date;
		private String usd;

		// Getters and Setters
		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getUsd() {
			return usd;
		}

		public void setUsd(String usd) {
			this.usd = usd;
		}
		
	    // Override equals
	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true; // 如果是同一個物件，則相等
	        if (obj == null || getClass() != obj.getClass()) return false; // 如果類型不同，不相等
	        CurrencyData that = (CurrencyData) obj;
	        return Objects.equals(date, that.date) &&
	               Objects.equals(usd, that.usd); // 比較 date 和 usd 的值是否相等
	    }

	    // Override hashCode
	    @Override
	    public int hashCode() {
	        return Objects.hash(date, usd); // 基於 date 和 usd 的值生成哈希值
	    }
	}
}
