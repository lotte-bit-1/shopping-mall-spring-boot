package com.bit.shoppingmall.app.dto.product;

import java.util.List;
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
public class ParameterForSubCategorySearch {
  private List<Long> id;
  private Long memberId;
  private int offset;

  public static ParameterForSubCategorySearch getParameterForSubCategorySearch(
      List<Long> id, Long memberId, int currentPage) {
    int offset = (currentPage - 1) * 9;
    return ParameterForSubCategorySearch.builder().id(id).offset(offset).memberId(memberId).build();
  }
}
