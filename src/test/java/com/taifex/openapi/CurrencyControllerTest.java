package com.taifex.openapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taifex.openapi.api.controller.CurrencyController;
import com.taifex.openapi.api.dto.CurrencyRequest;
import com.taifex.openapi.api.dto.CurrencyResponse.CurrencyData;
import com.taifex.openapi.rest.service.ExchangeRateService;

@WebMvcTest(CurrencyController.class)
@AutoConfigureMockMvc
public class CurrencyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ExchangeRateService service;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		objectMapper = new ObjectMapper();
	}

	@Test
	void checkCurrency_ShouldReturn200_WhenRequestIsValid() throws Exception {
		// Arrange
		CurrencyRequest request = new CurrencyRequest();
		request.setCurrency("USD");
		request.setStartDate("2025/01/13");
		request.setEndDate("2025/01/14");

		CurrencyData rate1 = new CurrencyData();
		rate1.setDate("20250113");
		rate1.setUsd("33.119000");

		CurrencyData rate2 = new CurrencyData();
		rate2.setDate("20250114");
		rate2.setUsd("32.971000");

		when(service.findExchangeRates(request.getCurrency(), request.getStartDate(), request.getEndDate()))
				.thenReturn(Arrays.asList(rate1, rate2));

		// Act & Assert
		mockMvc.perform(post("/api/forex").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.error.code").value("0000"))
				.andExpect(jsonPath("$.currency[0].date").value("20250113"))
				.andExpect(jsonPath("$.currency[0].usd").value("33.119000"))
				.andExpect(jsonPath("$.currency[1].date").value("20250114"))
				.andExpect(jsonPath("$.currency[1].usd").value("32.971000"));

		verify(service, times(1)).findExchangeRates(request.getCurrency(), request.getStartDate(),
				request.getEndDate());
	}

	@Test
	void checkCurrency_ShouldReturn400_WhenDateRangeIsInvalid() throws Exception {
		// Arrange
		CurrencyRequest request = new CurrencyRequest();
		request.setCurrency("USD");
		request.setStartDate("2030/01/01"); // Future date
		request.setEndDate("2030/01/05");

		// Act & Assert
		mockMvc.perform(post("/api/forex").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.code").value("E001"))
				.andExpect(jsonPath("$.error.message").value("日期區間不符"));

		verify(service, times(0)).findExchangeRates(any(), any(), any());
	}

	@Test
	void checkCurrency_ShouldReturn400_WhenDateFormatIsInvalid() throws Exception {
		// Arrange
		CurrencyRequest request = new CurrencyRequest();
		request.setCurrency("USD");
		request.setStartDate("invalid-date");
		request.setEndDate("2025/12/31");

		// Act & Assert
		mockMvc.perform(post("/api/forex").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error.code").value("E001"))
				.andExpect(jsonPath("$.error.message").value("日期區間不符"));

		verify(service, times(0)).findExchangeRates(any(), any(), any());
	}

	@Test
	void checkCurrency_ShouldReturnEmptyList_WhenNoExchangeRatesFound() throws Exception {
		// 準備階段
		CurrencyRequest request = new CurrencyRequest();
		request.setCurrency("USD");
		request.setStartDate("2024/11/01");
		request.setEndDate("2024/11/20");

		// 模擬行為
		when(service.findExchangeRates(request.getCurrency(), request.getStartDate(), request.getEndDate()))
				.thenReturn(Collections.emptyList());

		// 執行與驗證
		mockMvc.perform(post("/api/forex").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.error.code").value("0000")).andExpect(jsonPath("$.currency").isEmpty());

		// 驗證
		verify(service, times(1)).findExchangeRates(request.getCurrency(), request.getStartDate(),
				request.getEndDate());
	}
}
