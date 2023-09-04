package com.bit.shoppingmall.app.exception.likes;

import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

@Getter
public class LikesEntityDuplicateException extends DataIntegrityViolationException {

  private static final String message = "이미 추가된 상품입니다.";

  public LikesEntityDuplicateException() {
    super(LikesEntityDuplicateException.message);
  }

  public int getStatusCode() {
    return HttpServletResponse.SC_BAD_REQUEST;
  }
}
