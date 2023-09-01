package com.bit.shoppingmall.app.exception.category;

import com.bit.shoppingmall.app.exception.DomainException;

public class CategoryNotFoundException extends DomainException {

  private static final String message = "카테고리가 존재하지 않습니다";

  public CategoryNotFoundException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return 0;
  }
}
