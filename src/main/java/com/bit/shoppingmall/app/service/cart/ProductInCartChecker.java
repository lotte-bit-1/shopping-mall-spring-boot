package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.mapper.CartMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ProductInCartChecker implements
    CartValidationCheckerService<Cart> {

  private final CartMapper cartMapper;
  @Override
  public boolean valid(Cart cart) {
    return cartMapper.select(Cart.getCompKey(cart)).isPresent();
  }


}
