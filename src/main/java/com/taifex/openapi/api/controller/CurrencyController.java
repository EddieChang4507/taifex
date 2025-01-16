package com.taifex.openapi.api.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taifex.openapi.api.dto.CurrencyRequest;
import com.taifex.openapi.api.dto.CurrencyResponse;
import com.taifex.openapi.rest.entity.ExchangeRate;
import com.taifex.openapi.rest.service.ExchangeRateService;

@RestController
@RequestMapping("/api")
public class CurrencyController {

	@Autowired
	private ExchangeRateService service;

	@PostMapping("forex")
	public ResponseEntity<CurrencyResponse> checkCurrency(@RequestBody CurrencyRequest request) {
		CurrencyResponse response = new CurrencyResponse();

		// 驗證日期範圍
		if (!isValidDateRange(request.getStartDate(), request.getEndDate())) {
			CurrencyResponse.ErrorResponse error = new CurrencyResponse.ErrorResponse();
			error.setCode("E001");
			error.setMessage("日期區間不符");
			response.setError(error);
			return ResponseEntity.badRequest().body(response);
		}

		List<CurrencyResponse.CurrencyData> rtnList = service.findExchangeRates(request.getCurrency(),
				request.getStartDate(), request.getEndDate());

		response.setCurrency(rtnList);

		// 成功回應
		CurrencyResponse.ErrorResponse error = new CurrencyResponse.ErrorResponse();
		error.setCode("0000");
		error.setMessage("成功");
		response.setError(error);

		return ResponseEntity.ok(response);
	}

	private boolean isValidDateRange(String startDate, String endDate) {
		// 日期驗證邏輯（範例檢查：開始日期是否小於結束日期）
		if (startDate == null || endDate == null) {
			return false;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		try {
			LocalDate start = LocalDate.parse(startDate, formatter);
			LocalDate end = LocalDate.parse(endDate, formatter);
			LocalDate oneYearAgo = LocalDate.now().minusYears(1);
			LocalDate yesterday = LocalDate.now().minusDays(1);

			// 檢查日期範圍
			/**
			 * 開始日期不得晚於結束日期 開始日期必須在過去一年內或更晚 結束日期必須在昨天或更早
			 */
			return start.compareTo(end) <= 0 && !start.isBefore(oneYearAgo) && !end.isAfter(yesterday);

		} catch (DateTimeParseException e) {
			// 日期格式錯誤
			return false;
		}
	}
}
