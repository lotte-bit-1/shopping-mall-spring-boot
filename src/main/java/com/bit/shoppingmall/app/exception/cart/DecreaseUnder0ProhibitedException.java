package com.bit.shoppingmall.app.exception.cart;

import com.bit.shoppingmall.app.exception.DomainException;

public class DecreaseUnder0ProhibitedException extends
    DomainException {
  private static final String message = "카트에 존재하는 상품의 개수보다 더 많은 개수의 상품을 지울 수 없습니다.";
  public DecreaseUnder0ProhibitedException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return 0;
  }
}
