package com.bit.shoppingmall.app.dto.category.response;

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
public class CategoryHierarchy {

  private Long pp_id;
  private Long p_id;
  private Long id;
  private String pp_name;
  private String p_name;
  private String name;
  private Integer level;
}
