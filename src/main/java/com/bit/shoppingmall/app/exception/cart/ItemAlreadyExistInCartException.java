package com.bit.shoppingmall.app.exception.cart;

import com.bit.shoppingmall.app.exception.DomainException;

public class ItemAlreadyExistInCartException extends DomainException {

  private final static String message = "이미 장바구니에 존재하는 상품입니다.";

  public ItemAlreadyExistInCartException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return 0;
  }
}
