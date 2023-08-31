package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
@Qualifier("RemoveItemInCart")
public class RemoveItemInCart implements
    DecreaseItemInCartStrategy {
  private final CartMapper cartMapper;

  @Override
  public void decrease(Cart cart, Long requestQuantity) {
    if(cart.getProductQuantity() < requestQuantity){
      cartMapper.delete(Cart.getCompKey(cart));
    }
      cartMapper.update(Cart.getCompKey(cart),cart.getProductQuantity() -requestQuantity);
  }
}
