package com.taifex.openapi.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taifex.openapi.rest.entity.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

}
