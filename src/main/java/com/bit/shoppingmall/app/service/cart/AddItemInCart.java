package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import com.bit.shoppingmall.app.mapper.CartMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddItemInCart {
  private final PutItemIntoCartStrategy addItemStrategy;
  private final CartMapper dao;
  public void add(ProductAndMemberCompositeKey compKey, Long requestQuantity){
    Optional<Cart> cartOptional = dao.select(compKey);
    cartOptional.ifPresent(cart -> addItemStrategy.put(cart, requestQuantity));
    dao.insert(Cart.cartBuilder(compKey,requestQuantity));
  }

}
