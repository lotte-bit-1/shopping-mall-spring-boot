package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.exception.member.MemberEntityNotFoundException;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberExistChecker implements
    CartValidationCheckerService<Cart> {

  private final MemberMapper memberDao;

  @Override
  public void valid(Cart cart) {
    memberDao.select(cart.getMemberId()).orElseThrow(MemberEntityNotFoundException::new);
  }
}
