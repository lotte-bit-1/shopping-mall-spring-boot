package com.bit.shoppingmall.app.dto.cart;

import com.bit.shoppingmall.app.dto.product.ProductItemQuantity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProductQuantityInfo {

  private Long productId;
  private Integer stock;

  public static CartProductQuantityInfo getQuantityInfoFromProduct(
      ProductItemQuantity productItemQuantity) {
    return new CartProductQuantityInfo(
        productItemQuantity.getId(), productItemQuantity.getQuantity());
  }
}
