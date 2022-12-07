package com.skye.libra.controller;

import com.skye.libra.model.Demo;
import com.skye.libra.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * demo.
 *
 * @author : gh
 * 2022/12/7
 */
@Controller
@RequestMapping("demo")
public class DemoController {

	@Autowired
	private IDemoService demoService;

	@GetMapping("/get/{id}")
	public Demo getDemoById(@PathVariable("id") String id) {
		return demoService.getById(id);
	}

	/**
	 * 使用@Controller注解，需要将请求的参数转为json格式来解析，在参数中加入@RequestBody注解
	 * @param map map.
	 * @return object.
	 */
	@PostMapping("/post")
	public Object postDemo(@RequestBody Map<String, Object> map) {
		return null;
	}


}
