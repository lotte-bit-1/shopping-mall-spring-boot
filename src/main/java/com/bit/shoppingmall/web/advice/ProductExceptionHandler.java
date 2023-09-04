package com.bit.shoppingmall.web.advice;

import com.bit.shoppingmall.app.exception.product.ProductKeywordNotMatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/product")
@ControllerAdvice(annotations = Controller.class)
public class ProductExceptionHandler {
  @ExceptionHandler(ProductKeywordNotMatchException.class)
  public String handleProductKeywordNotMatchException(
      ProductKeywordNotMatchException exception, Model model) {
    model.addAttribute("error", exception.getMessage());
    return "product/list";
  }
}
