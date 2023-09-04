package com.bit.shoppingmall.app.exception.product;

import com.bit.shoppingmall.app.exception.DomainException;
import javax.servlet.http.HttpServletResponse;

public class ProductKeywordNotMatchException extends DomainException {
  private static final String message = "키워드와 일치하는 상품이 없습니다";

  public ProductKeywordNotMatchException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_NOT_FOUND;
  }
}
