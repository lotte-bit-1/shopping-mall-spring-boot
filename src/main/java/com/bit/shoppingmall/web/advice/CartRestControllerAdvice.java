package com.bit.shoppingmall.web.advice;

import com.bit.shoppingmall.app.exception.cart.OutOfStockException;
import com.bit.shoppingmall.app.exception.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(1)
@RestControllerAdvice(annotations = RestController.class)
public class CartRestControllerAdvice {

  @ExceptionHandler(OutOfStockException.class)
  public ResponseEntity<ErrorResponse> outOfStockException(OutOfStockException e) {
    int statusCode = e.getStatusCode();

    ErrorResponse body = ErrorResponse.builder()
        .code(String.valueOf(statusCode))
        .message(e.getMessage())
        .build();

    return ResponseEntity.status(statusCode).body(body);
  }



}
