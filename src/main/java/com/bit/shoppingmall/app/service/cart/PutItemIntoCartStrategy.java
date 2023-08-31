package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import org.springframework.stereotype.Service;

@Service
public interface PutItemIntoCartStrategy {
  void put(Cart cart, Long requestQuantity);
}
