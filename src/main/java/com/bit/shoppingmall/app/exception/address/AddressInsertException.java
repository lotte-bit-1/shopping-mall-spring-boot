package com.bit.shoppingmall.app.exception.address;

import com.bit.shoppingmall.app.exception.DomainException;
import javax.servlet.http.HttpServletResponse;

public class AddressInsertException extends DomainException {
  private static final String message = "주소지 등록에 실패하였습니다";

  public AddressInsertException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_BAD_REQUEST;
  }
}
