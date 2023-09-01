package com.bit.shoppingmall.app.dto.member.request;

import com.bit.shoppingmall.app.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterDto {

  @NotBlank(message = "이메일은 필수 입니다.")
  private String email;
  @NotBlank(message = "비밀번호는 필수 입니다.")
  @Size(min = 8, max = 16, message = "비밀번호는 8 ~ 16자 이내입니다")
  private String password;
  @NotBlank(message = "이름은 필수 잆니다.")
  @Size(min = 2, max = 20, message = "이름은 2 ~ 20자 이내 입니다.")
  private String name;

  public Member toEntity(String hashedPassword) {
    return Member.builder().email(email).password(hashedPassword).name(name).build();
  }
}
