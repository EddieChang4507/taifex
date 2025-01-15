package com.taifex.openapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taifex.openapi.dto.RequestDto;

@RestController
@RequestMapping("/api")
public class ForexController {
	
//	@PostMapping("/forex")
//	public ResponseEntity<String> submitData(@RequestBody RequestDto data) {
//		// 處理數據邏輯
//		return ResponseEntity.ok("Data received: " + data);
//	}
	
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello, World!");
    }
}
