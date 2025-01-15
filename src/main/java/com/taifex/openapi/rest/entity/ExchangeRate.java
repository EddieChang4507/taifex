package com.taifex.openapi.rest.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exchange_rate")
public class ExchangeRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "currency", nullable = false, length = 10)
	private String currency;

	@Column(name = "exchange_rate", nullable = false, precision = 10, scale = 6)
	private BigDecimal exchangeRate;

	@Column(name = "exchange_rate_date", nullable = false)
	private LocalDate exchangeRateDate;

	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public LocalDate getExchangeRateDate() {
		return exchangeRateDate;
	}

	public void setExchangeRateDate(LocalDate exchangeRateDate) {
		this.exchangeRateDate = exchangeRateDate;
	}

}
