package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.exception.cart.DecreaseUnder0ProhibitedException;
import com.bit.shoppingmall.app.mapper.CartMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier("ThrowAnErrorUnder0Quantity")
public class ThrowAnErrorUnder0Quantity implements
    DecreaseItemInCartStrategy {

  private final CartMapper dao;

  @Override
  public void decrease(Cart cart, Long requestQuantity) {
    if (cart.getProductQuantity() < requestQuantity) {
      throw new DecreaseUnder0ProhibitedException();
    }
  }
}
