package com.bit.shoppingmall.web.advice;

import com.bit.shoppingmall.app.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice(basePackages = "com.bit.shoppingmall.web.controller")
public class CommonControllerAdvice {

    @ExceptionHandler(DomainException.class)
    public ModelAndView domainException(DomainException ex, Model model) {
        log.warn("WARN={}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return new ModelAndView("redirect:/");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingServletRequestParameterException(MissingServletRequestParameterException ex,
                                                                      Model model) {
        log.error("ERROR={}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return new ModelAndView("redirect:/");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                        HttpServletRequest httpServletRequest, Model model) {
        log.error("ERROR={}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());

        String requestURI = httpServletRequest.getRequestURI();
        return new ModelAndView(String.format("redirect:%s", requestURI));
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView runtimeException(Exception ex, Model model) {
        log.error("ERROR={}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return new ModelAndView("redirect:/");
    }
}
