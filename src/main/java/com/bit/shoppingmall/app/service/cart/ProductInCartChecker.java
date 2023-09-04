package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.exception.cart.CartNotFoundException;
import com.bit.shoppingmall.app.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ProductInCartChecker implements
    CartValidationCheckerService<Cart> {

  private final CartMapper cartMapper;
  @Override
  public void valid(Cart cart) {
    cartMapper.select(Cart.getCompKey(cart)).orElseThrow(CartNotFoundException::new);
  }


}
