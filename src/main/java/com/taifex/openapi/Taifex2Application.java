package com.taifex.openapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = { "com.taifex.openapi.scheduler" , "com.taifex.openapi.*"}) // ComponentScan 的實際包路徑
public class Taifex2Application {

	public static void main(String[] args) {
		SpringApplication.run(Taifex2Application.class, args);
	}

}
