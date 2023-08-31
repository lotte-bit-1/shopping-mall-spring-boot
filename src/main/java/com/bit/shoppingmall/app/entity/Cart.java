package com.bit.shoppingmall.app.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

  @NonNull
  private Long memberId;
  @NonNull
  private Long productId;
  @NonNull
  private Long productQuantity;

  public static Cart cartBuilder(
      ProductAndMemberCompositeKey productAndMemberCompositeKey, Long productQuantity) {
    return new Cart(
        productAndMemberCompositeKey.getMemberId(),
        productAndMemberCompositeKey.getProductId(),
        productQuantity);
  }

  public static ProductAndMemberCompositeKey getCompKey(Cart c){
    return new ProductAndMemberCompositeKey(c.getProductId(),c.getMemberId());
  }

  public static Cart updateCart(Cart cart, Long requestQuantity) {
    return Cart.cartBuilder(
        new ProductAndMemberCompositeKey(cart.getProductId(), cart.getMemberId()), requestQuantity);
  }
}
