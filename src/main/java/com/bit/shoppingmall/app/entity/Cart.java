package com.bit.shoppingmall.app.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@RequiredArgsConstructor
@ToString
public class Cart {

  @NonNull
  private Long memberId;
  @NonNull
  private Long productId;
  @NonNull
  private Long productQuantity;

  public static Cart cartBuilder(
      ProductAndMemberCompositeKey productAndMemberCompositeKey, Long productQuantity) {
    return Cart.builder().memberId(productAndMemberCompositeKey.getMemberId())
        .productId(productAndMemberCompositeKey.getProductId()).productQuantity(productQuantity)
        .build();
  }

  public static ProductAndMemberCompositeKey getCompKey(Cart c){
    return new ProductAndMemberCompositeKey(c.getProductId(),c.getMemberId());
  }

  public static Cart updateCart(ProductAndMemberCompositeKey compKey, Long requestQuantity){
    return cartBuilder(compKey,requestQuantity);
  }
}
