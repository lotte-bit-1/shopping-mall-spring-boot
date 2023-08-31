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
public class ProductDetail {

  private Long id;
  private String name;
  private Long price;
  private Integer categoryId;
  private String description;
  private String url;
  private Long quantity;
  private Boolean isLiked;
  private String code;
}
