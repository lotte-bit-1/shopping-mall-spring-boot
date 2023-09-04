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
public class ParameterForSearchProductForKeyword {
  private String keyword;
  private Long userId;
  private int offset;

  public static ParameterForSearchProductForKeyword getMapperParameter(
      String keyword, Long userId, int currentPage) {
    int offset = (currentPage - 1) * 9;

    return ParameterForSearchProductForKeyword.builder()
        .keyword(keyword)
        .userId(userId)
        .offset(offset)
        .build();
  }
}
