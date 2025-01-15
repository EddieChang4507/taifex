package com.taifex.openapi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.taifex.openapi.scheduler.CheckExchangeRate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExchangeRateSchedulerTest {

	@Autowired
	private CheckExchangeRate scheduler;

	@Test
	void testExecuteExchangeRateCheck() {
		scheduler.executeExchangeRateCheck();;
		assertTrue(true);
	}
}
