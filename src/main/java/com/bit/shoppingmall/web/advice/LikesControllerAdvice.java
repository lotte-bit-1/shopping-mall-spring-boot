package com.bit.shoppingmall.web.advice;

import com.bit.shoppingmall.app.exception.likes.LikesEntityDuplicateException;
import com.bit.shoppingmall.app.exception.likes.LikesEntityNotFoundException;
import com.bit.shoppingmall.app.exception.response.ErrorResponse;
import com.bit.shoppingmall.web.controller.LikesController;
import com.bit.shoppingmall.web.restcontroller.LikesRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Order(0)
@ControllerAdvice(assignableTypes = {LikesController.class, LikesRestController.class})
public class LikesControllerAdvice {

  @ExceptionHandler(LikesEntityNotFoundException.class)
  public ModelAndView domainException(LikesEntityNotFoundException e, Model model) {
    log.warn("WARN={}", e.getMessage());
    model.addAttribute("errorMessage", e.getMessage());
    return new ModelAndView("redirect:/likes");
  }

  @ExceptionHandler(LikesEntityDuplicateException.class)
  public ResponseEntity<ErrorResponse> likesEntityDuplicateException(LikesEntityDuplicateException e) {
    int statusCode = e.getStatusCode();

    ErrorResponse body = ErrorResponse.builder()
            .code(String.valueOf(statusCode))
            .message(e.getMessage())
            .build();

    return ResponseEntity.status(statusCode).body(body);
  }
}
