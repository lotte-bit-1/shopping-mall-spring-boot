package com.bit.shoppingmall.app.dto.cart;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompKeyWithPagination {

  private Long productId;
  private Long memberId;
  private Long start;
  private Long end;
}
