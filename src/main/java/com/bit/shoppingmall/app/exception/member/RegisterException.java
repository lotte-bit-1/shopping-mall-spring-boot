package com.bit.shoppingmall.app.exception.member;

import com.bit.shoppingmall.app.exception.DomainException;

public class RegisterException extends DomainException {

  private static final String message = "회원가입에 실패하였습니다.";

  public RegisterException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return 0;
  }
}
