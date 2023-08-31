package com.bit.shoppingmall.app.exception.delivery;

import com.bit.shoppingmall.app.exception.EntityNotFoundException;

public class DeliveryEntityNotFoundException extends EntityNotFoundException {

  private static final String message = "배송 정보를 찾을 수 없습니다.";

  public DeliveryEntityNotFoundException() {
    super(message);
  }
}
