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
public class Member extends BaseEntity {

  private Long id;
  @NonNull
  private String email;
  @NonNull
  private String password;
  @NonNull
  private String name;
  @Builder.Default
  private Long money = 0L;

  public void updateMoney(Long money) {
    this.money = money;
  }
}
