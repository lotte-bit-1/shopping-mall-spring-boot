package com.bit.shoppingmall.app.exception.order;

import com.bit.shoppingmall.app.exception.DomainException;
import javax.servlet.http.HttpServletResponse;

/* 주문 상태를 변경할 때 에러 */
public class OrderUpdateStatusException extends DomainException {

  private static final String message = "존재하지 않는 주문입니다.";

  public OrderUpdateStatusException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_NOT_FOUND;
  }
}
