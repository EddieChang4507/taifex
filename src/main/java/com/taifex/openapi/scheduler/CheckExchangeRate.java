package com.taifex.openapi.scheduler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taifex.openapi.rest.entity.ExchangeRate;
import com.taifex.openapi.rest.service.ExchangeRateService;
import com.taifex.openapi.result.DailyForeignExchangeRatesResult;
import com.taifex.openapi.service.ApiService;

@Component
public class CheckExchangeRate {

	private static final Logger logger = LoggerFactory.getLogger(CheckExchangeRate.class);

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private final ApiService apiService = new ApiService();

	@Autowired
	private ExchangeRateService exchangeRateService;

	// 從 properties 文件中讀取重試次數
	@Value("${exchange.rate.retry.count}")
	private int retryCount;

	// 從 properties 文件中讀取重試間隔毫秒數
	@Value("${exchange.rate.retry.time}")
	private int retryTime;

	// 從 properties 文件中讀取getUrl
	@Value("${daily.foreign.exchange.rates.url}")
	private String url;

	// 定義輸入和輸出的日期格式
	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	// 設定每天 18:00 執行
	@Scheduled(cron = "0 39 16 * * *")
	public void checkExchangeRate() {
		executeExchangeRateCheck();
	}

	public void executeExchangeRateCheck() {
		// 匯率邏輯
		boolean success = false;

		for (int attempt = 1; attempt <= retryCount; attempt++) {
			logger.info("嘗試第 {} 次請求...", attempt);
			DailyForeignExchangeRatesResult rqt = executeRequest(url);
			success = rqt.isSuccess();
			if (success) {
				logger.info("第 {} 次請求成功", attempt);

				logger.info(rqt.getData().toString());

				String currency = "USD/NTD";
				for (int i = 0; i < rqt.getData().size(); i++) {
					Map<String, Object> oriMap = (Map<String, Object>) rqt.getData().get(i);
					ExchangeRate exchangeRate = new ExchangeRate();
					exchangeRate.setCurrency(currency);
					exchangeRate.setExchangeRate(new BigDecimal(oriMap.get(currency).toString()));
					LocalDate date = LocalDate.parse(oriMap.get("Date").toString(), inputFormatter);
					String formattedDate = date.format(outputFormatter);
					exchangeRate.setExchangeRateDate(LocalDate.parse(formattedDate));
					exchangeRateService.saveExchangeRate(exchangeRate);
				}

				break;
			}
			logger.warn("第 {} 次請求失敗", attempt);
			try {
				Thread.sleep(retryTime);
			} catch (Exception e) {
				logger.info("第 {} 次請求失敗，發生異常:{}", attempt, e.getStackTrace());
			}
		}

		if (success) {
			logger.info("每日排程執行完成");
		} else {
			logger.error("每日排程執行失敗，嘗試 {} 次後仍未成功", retryCount);
		}
	}

	private DailyForeignExchangeRatesResult executeRequest(String url) {
		try {
			String response = apiService.sendGetRequest(url);
			if (response != null) {
				// 將 JSON 字串解析為 List<Map<String, Object>>
				List<Map<String, Object>> data = objectMapper.readValue(response,
						new TypeReference<List<Map<String, Object>>>() {
						});
				return new DailyForeignExchangeRatesResult(true, "請求成功", data);
			} else {
				return new DailyForeignExchangeRatesResult(false, "請求失敗，回應為空", null);
			}
		} catch (Exception e) {
			logger.error("請求失敗，發生異常: {}", e.getMessage());
			return new DailyForeignExchangeRatesResult(false, "請求失敗，發生異常: " + e.getMessage(), null);
		}
	}

}
