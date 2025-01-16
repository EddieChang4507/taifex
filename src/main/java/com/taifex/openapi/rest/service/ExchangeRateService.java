package com.taifex.openapi.rest.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.taifex.openapi.api.dto.CurrencyResponse;
import com.taifex.openapi.rest.entity.ExchangeRate;
import com.taifex.openapi.rest.repository.ExchangeRateRepository;

import jakarta.persistence.criteria.Predicate;

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
		ExchangeRate exchangeRate = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("ExchangeRate not found"));
		exchangeRate.setCurrency(exchangeRateDetails.getCurrency());
		exchangeRate.setExchangeRate(exchangeRateDetails.getExchangeRate());
		exchangeRate.setExchangeRateDate(exchangeRateDetails.getExchangeRateDate());
		return repository.save(exchangeRate);
	}

	// Delete
	public void deleteExchangeRate(Long id) {
		repository.deleteById(id);
	}

	public List<CurrencyResponse.CurrencyData> findExchangeRates(String currency, String startDateOrigin,
			String endDateOrigin) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate startDate = LocalDate.parse(startDateOrigin, formatter);
		LocalDate endDate = LocalDate.parse(endDateOrigin, formatter);

		Specification<ExchangeRate> spec = (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (currency != null && !currency.isEmpty()) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("currency")),
						"%" + currency.toUpperCase() + "%"));
			}
			if (startDate != null && endDate != null) {
				predicates.add(criteriaBuilder.between(root.get("exchangeRateDate"), startDate, endDate));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};

		List<ExchangeRate> rtnList = repository.findAll(spec);
		List<CurrencyResponse.CurrencyData> currencyDataList = new ArrayList<>();
		for (int i = 0; i < rtnList.size(); i++) {
			CurrencyResponse.CurrencyData data = new CurrencyResponse.CurrencyData();
			ExchangeRate rspMap = rtnList.get(i);
			data.setDate(rspMap.getExchangeRateDate().toString().replace("-", ""));
			data.setUsd(rspMap.getExchangeRate().toString());
			currencyDataList.add(data);
		}

		// 去除重複資料
		currencyDataList = currencyDataList.stream().distinct().collect(Collectors.toList());

		return currencyDataList;
	}
}
