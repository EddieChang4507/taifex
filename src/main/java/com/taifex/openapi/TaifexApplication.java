package com.taifex.openapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication(scanBasePackages = "com.taifex.openapi")
@SpringBootApplication
@EnableScheduling
//@ComponentScan(basePackages = { "com.taifex.openapi.dao" }) // , "com.taifex.openapi.scheduler"
@MapperScan("com.taifex.openapi.dao.mapper") // Mapper 的實際包路徑
@ComponentScan(basePackages = {"com.taifex.openapi.dao", "com.taifex.openapi.scheduler"}) // ComponentScan 的實際包路徑
public class TaifexApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaifexApplication.class, args);
	}

}
