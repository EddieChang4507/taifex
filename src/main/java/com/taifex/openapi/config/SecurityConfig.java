package com.taifex.openapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.taifex.openapi.rest.service.ExchangeRateService;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
		return http.build();
	}

    @Bean
    public ExchangeRateService exchangeRateService() {
        return new ExchangeRateService();
    }
}