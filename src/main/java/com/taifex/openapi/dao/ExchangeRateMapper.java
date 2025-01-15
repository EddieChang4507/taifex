package com.taifex.openapi.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExchangeRateMapper {
	void insertCase001(Map<String, Object> params);
}


