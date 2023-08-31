package com.bit.shoppingmall.app.exception.member;

import com.bit.shoppingmall.app.exception.DomainException;

public class MemberNotFoundException extends DomainException {

  private static final String errorMessage = "로그인을 먼저 하십시오";

  public MemberNotFoundException() {
    super(errorMessage);
  }

  @Override
  public int getStatusCode() {
    return 402;
  }
}
