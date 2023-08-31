package com.bit.shoppingmall.app.dto.member.response;

import com.bit.shoppingmall.app.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class MemberDetail {

  private Long id;
  private String email;
  private String name;
  private Long money;

  public static MemberDetail of(Member member) {
    return MemberDetail.builder()
        .id(member.getId())
        .email(member.getEmail())
        .name(member.getName())
        .money(member.getMoney())
        .build();
  }
}
