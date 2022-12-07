package com.skye.libra.service.impl;

import com.skye.libra.dao.DemoMapper;
import com.skye.libra.model.Demo;
import com.skye.libra.service.IDemoService;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @author : gh
 * 2022/12/7
 */
@Service
public class IDemoServiceImpl implements IDemoService {

	private final DemoMapper demoMapper;

	/**
	 * 现在推荐用构造器的方式引入依赖；
	 * @param demoMapper
	 */
	public IDemoServiceImpl(DemoMapper demoMapper) {
		this.demoMapper = demoMapper;
	}

	@Override
	public Demo getById(String id) {
		return demoMapper.getById(id);
	}
}
