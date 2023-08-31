package com.bit.shoppingmall.app.exception.order;

import com.bit.shoppingmall.app.exception.DomainException;
import javax.servlet.http.HttpServletResponse;

/* 주문 취소할 때 배송중일 경우 에러 */
public class OrderDeliveryProcessingException extends DomainException {

  private static final String message = "이미 배송중입니다.";

  public OrderDeliveryProcessingException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_NOT_FOUND;
  }
}
