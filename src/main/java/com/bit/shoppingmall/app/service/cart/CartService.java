package com.bit.shoppingmall.app.service.cart;

import com.bit.shoppingmall.app.dto.cart.AllCartProductInfoDtoWithPagination;
import com.bit.shoppingmall.app.dto.cart.CartAndProductDto;
import com.bit.shoppingmall.app.entity.Cart;
import com.bit.shoppingmall.app.entity.ProductAndMemberCompositeKey;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
  public void order(Cart cart);
  public void putItemInCart(ProductAndMemberCompositeKey compKey,Long quantity);
  public void deleteItemInCart(ProductAndMemberCompositeKey compKey);
  public void updateItemInCart(ProductAndMemberCompositeKey compKey, Long quantity);
  public AllCartProductInfoDtoWithPagination getCartByMember(Long memberId);
}
