package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import org.springframework.stereotype.Service;

@Service
public interface CartValidationCheckerService<T extends Cart> {
  void valid(T cart);
}
