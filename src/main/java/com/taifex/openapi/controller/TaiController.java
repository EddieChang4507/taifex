package com.taifex.openapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class TaiController {

	@GetMapping("test")
	public String hello() {
		return "hello world";
	}

}
