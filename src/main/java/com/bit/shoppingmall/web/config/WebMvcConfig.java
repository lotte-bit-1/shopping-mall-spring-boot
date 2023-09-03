package com.bit.shoppingmall.web.config;

import com.bit.shoppingmall.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/img/**", "/js/**", "/fonts/**", "/sass/**",
                        "/member/registerForm", "/", "/api/members/loginCheck/**", "/api/members/login", "/api/members/kakaoLogin",
                        "/product/**");
    }
}
