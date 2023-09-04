package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.exception.cart.ItemAlreadyExistInCartException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThrowErrorWhenThereIsProductInCart implements
    PutItemIntoCartStrategy {
  @Override
  public void put(Cart cart, Long requestQuantity){
      throw new ItemAlreadyExistInCartException();
  }
}
