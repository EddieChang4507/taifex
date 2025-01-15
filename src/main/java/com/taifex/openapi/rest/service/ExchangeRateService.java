package com.taifex.openapi.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taifex.openapi.rest.entity.ExchangeRate;
import com.taifex.openapi.rest.repository.ExchangeRateRepository;

@Service
public class ExchangeRateService {

	@Autowired
	private ExchangeRateRepository repository;

	// Create
	public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) {
		return repository.save(exchangeRate);
	}

	// Read all
	public List<ExchangeRate> getAllExchangeRate() {
		return repository.findAll();
	}

	// Read by ID
	public Optional<ExchangeRate> getExchangeRateById(Long id) {
		return repository.findById(id);
	}

	// Update
	public ExchangeRate updateExchangeRate(Long id, ExchangeRate exchangeRateDetails) {
		ExchangeRate exchangeRate = repository.findById(id).orElseThrow(() -> new RuntimeException("ExchangeRate not found"));
		exchangeRate.setCurrency(exchangeRateDetails.getCurrency());
		exchangeRate.setExchangeRate(exchangeRateDetails.getExchangeRate());
		exchangeRate.setExchangeRateDate(exchangeRateDetails.getExchangeRateDate());
		return repository.save(exchangeRate);
	}

	// Delete
	public void deleteExchangeRate(Long id) {
		repository.deleteById(id);
	}

}

