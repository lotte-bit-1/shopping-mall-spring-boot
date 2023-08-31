package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MemberExistChecker implements
    CartValidationCheckerService {

  private final MemberMapper memberDao;

  @Override
  public boolean valid(Cart cart) {
      return memberDao.select(cart.getMemberId()).isPresent();
  }
}
