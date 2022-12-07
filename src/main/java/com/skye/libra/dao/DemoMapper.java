package com.skye.libra.dao;

import com.skye.libra.model.Demo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DemoMapper {
	Demo getById(@Param("id") String id);
}
