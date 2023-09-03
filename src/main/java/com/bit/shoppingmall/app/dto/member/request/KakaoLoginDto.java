package com.bit.shoppingmall.app.dto.member.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class KakaoLoginDto {

    @NotBlank(message = "이메일은 필수 입니다.")
    private String email;
    @NotBlank(message = "이름은 필수 잆니다.")
    @Size(min = 2, max = 20, message = "이름은 2 ~ 20자 이내 입니다.")
    private String nickname;
}
