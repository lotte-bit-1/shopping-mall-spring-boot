package com.bit.shoppingmall.app.dto.member.request;

import com.bit.shoppingmall.app.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterDto {

  private String email;
  private String password;
  private String name;

  public Member toEntity(String hashedPassword) {
    return Member.builder().email(email).password(hashedPassword).name(name).build();
  }
}
