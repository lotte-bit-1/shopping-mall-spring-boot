package com.bit.shoppingmall.app.dto.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ParameterForProductList {
  private Long userId;
  private int offset;

  public static ParameterForProductList getProductListParameter(Long userId, int currentPage) {
    int offset = (currentPage - 1) * 9;
    return ParameterForProductList.builder().offset(offset).userId(userId).build();
  }
}
