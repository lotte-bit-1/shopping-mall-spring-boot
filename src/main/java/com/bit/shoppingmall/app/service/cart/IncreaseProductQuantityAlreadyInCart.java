package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import com.bit.shoppingmall.app.exception.cart.OutOfStockException;
import com.bit.shoppingmall.app.mapper.CartMapper;
import com.bit.shoppingmall.app.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Primary
public class IncreaseProductQuantityAlreadyInCart implements
    PutItemIntoCartStrategy {

  private final CartMapper cartDao;
  private final ProductMapper productDao;

  @Override
  public void put(Cart cart, Long requestQuantity) {
    int stock = productDao.selectProductQuantity(cart.getProductId());
    if (requestQuantity < stock) {
      cartDao.update(Cart.updateCart(Cart.getCompKey(cart),requestQuantity));
    }

   else{
     throw new OutOfStockException();
    }

  }
}
