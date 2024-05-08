package com.mshop.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.mshop.*")
@EnableFeignClients
@EnableAutoConfiguration
public class MonitorShopProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorShopProductServiceApplication.class, args);
	}

}
