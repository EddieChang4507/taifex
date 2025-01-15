package com.taifex.openapi.result;

import java.util.List;

public class DailyForeignExchangeRatesResult {

	private boolean success; // 是否成功
	private String message; // 訊息
	private List<?> data; // 回傳的資料 (泛型支援 JSON Array)

	public DailyForeignExchangeRatesResult(boolean success, String message, List<?> data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public List<?> getData() {
		return data;
	}

	@Override
	public String toString() {
		return "RequestResult{" + "success=" + success + ", message='" + message + '\'' + ", data=" + data + '}';
	}
}
