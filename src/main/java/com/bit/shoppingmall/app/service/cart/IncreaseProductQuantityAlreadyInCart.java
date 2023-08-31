package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
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
@Qualifier("IncreaseProductQuantityAlreadyInCart")
public class IncreaseProductQuantityAlreadyInCart implements
    PutItemIntoCartStrategy {

  private final CartMapper cartDao;
  private final ProductMapper productDao;

  @Override
  public void put(Cart cart, Long requestQuantity) {
    int stock = productDao.checkQuantity(cart.getProductId());
    if (requestQuantity < stock) {
      cartDao.update(Cart.getCompKey(cart), cart.getProductQuantity() + requestQuantity);
    }
    throw new OutOfStockException();

  }
}
