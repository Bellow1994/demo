package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/demo")
@RestController
public class TestController {
	
	@GetMapping("/test")
    public String getKeywordIndexData() {
        return "Hello world !";
    }
}
