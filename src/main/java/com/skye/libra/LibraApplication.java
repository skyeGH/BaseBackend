package com.skye.libra;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.skye.libra"}, exclude = {SecurityAutoConfiguration.class})
@MapperScan(basePackages = {"com.skye.libra.**.dao"})
public class LibraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraApplication.class, args);
	}

}
