package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log
public class CartOrderValidationProcessor {

  private List<CartValidationCheckerService<Cart>> list;

  public void setValidationChecker(List<CartValidationCheckerService<Cart>> list) {
    this.list = list;
  }

  public void process(Cart cart) {
    list.forEach(check -> check.valid(cart));
  }


}
