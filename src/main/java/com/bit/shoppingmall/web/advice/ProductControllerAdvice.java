package com.bit.shoppingmall.web.advice;

import com.bit.shoppingmall.app.exception.product.ProductKeywordNotMatchException;
import com.bit.shoppingmall.web.controller.OrderController;
import com.bit.shoppingmall.web.controller.ProductController;
import com.bit.shoppingmall.web.restcontroller.OrderRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Order(0)
@ControllerAdvice(assignableTypes = {ProductController.class})
public class ProductControllerAdvice {

  @ExceptionHandler(ProductKeywordNotMatchException.class)
  public String domainException(ProductKeywordNotMatchException ex, Model model) {
    log.warn("WARN={}", ex.getMessage());
    model.addAttribute("error", ex.getMessage());
    return "product/list";
  }
}
