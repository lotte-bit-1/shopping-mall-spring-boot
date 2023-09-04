package com.bit.shoppingmall.web.advice;

import com.bit.shoppingmall.app.exception.likes.LikesEntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class LikesControllerAdvice {

  @ExceptionHandler(LikesEntityNotFoundException.class)
  public ModelAndView domainException(LikesEntityNotFoundException e, Model model) {
    log.warn("WARN={}", e.getMessage());
    model.addAttribute("errorMessage", e.getMessage());
    return new ModelAndView("redirect:/likes");
  }
}
