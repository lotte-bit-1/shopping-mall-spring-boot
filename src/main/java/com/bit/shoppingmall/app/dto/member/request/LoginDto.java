package com.bit.shoppingmall.app.dto.member.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class LoginDto {

  @NotBlank(message = "이메일은 필수 입니다.")
  private String email;
  @NotBlank(message = "비밀번호는 필수 입니다.")
  @Size(min = 8, max = 16, message = "비밀번호는 8 ~ 16자 이내입니다")
  private String password;
}
