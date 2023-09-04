package com.bit.shoppingmall.web.advice;

import com.bit.shoppingmall.app.exception.product.ProductKeywordNotMatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Order(0)
@ControllerAdvice(annotations = Controller.class)
public class ProductControllerAdvice {

    @ExceptionHandler(ProductKeywordNotMatchException.class)
    public ModelAndView domainException(ProductKeywordNotMatchException ex, Model model) {
        log.warn("WARN={}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return new ModelAndView("redirect:/product/1/list");
    }
}
