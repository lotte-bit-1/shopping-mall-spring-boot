package com.bit.shoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ShoppingMallSpringApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(ShoppingMallSpringApplication.class, args);
  }

  @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ShoppingMallSpringApplication.class);
	}

}
