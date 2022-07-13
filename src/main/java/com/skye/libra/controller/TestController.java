package com.skye.libra.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/get")
	public Object getTest() {
		return "get";
	}
	@PostMapping("/post")
	public Object postTest(@RequestBody Map<String, Object> map) {
		return map;
	}
}
