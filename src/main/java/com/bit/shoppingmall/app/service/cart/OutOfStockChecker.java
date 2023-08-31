package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.entity.Product;
import com.bit.shoppingmall.app.exception.cart.OutOfStockException;

public class OutOfStockChecker {

  public void isStockEnough(Product product, Long requestQuantity){
    if(product.getQuantity() < requestQuantity){
      throw new OutOfStockException();
    }
  }
}