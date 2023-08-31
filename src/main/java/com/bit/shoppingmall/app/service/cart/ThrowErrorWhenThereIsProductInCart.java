package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.exception.cart.ItemAlreadyExistInCartException;
import com.bit.shoppingmall.app.mapper.CartMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier("ThrowErrorWhenThereIsProductInCart")
public class ThrowErrorWhenThereIsProductInCart implements
    PutItemIntoCartStrategy {
  @Override
  public void put(Cart cart, Long requestQuantity){
      throw new ItemAlreadyExistInCartException();
  }
}
