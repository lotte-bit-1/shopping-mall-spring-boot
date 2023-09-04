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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Address extends BaseEntity {

  private Long id;
  @NonNull
  private Long memberId;
  @Builder.Default
  private boolean isDefault = false;
  @NonNull
  private String roadName;
  @NonNull
  private String addrDetail;
  @NonNull
  private String zipCode;
}
