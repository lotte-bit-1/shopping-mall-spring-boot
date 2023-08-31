package com.bit.shoppingmall.app.exception.member;

import com.bit.shoppingmall.app.exception.DomainException;
import javax.servlet.http.HttpServletResponse;

public class LoginFailException extends DomainException {

  private static final String message = "로그인에 실패하였습니다.";

  public LoginFailException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_BAD_REQUEST;
  }
}
