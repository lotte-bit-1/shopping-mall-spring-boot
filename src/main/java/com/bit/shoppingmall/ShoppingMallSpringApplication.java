package com.bit.shoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ShoppingMallSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingMallSpringApplication.class, args);
    }

}
