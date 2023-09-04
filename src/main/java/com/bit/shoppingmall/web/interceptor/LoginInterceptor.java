package com.bit.shoppingmall.web.interceptor;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("Interceptor URI: "+ request.getRequestURI());
        log.info("interceptor URL: " + request.getRequestURI());
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("loginMember") == null) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
