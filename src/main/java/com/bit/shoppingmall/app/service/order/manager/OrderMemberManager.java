package com.bit.shoppingmall.app.service.order.manager;

import com.bit.shoppingmall.app.dto.member.response.OrderMemberDetail;
import com.bit.shoppingmall.app.entity.Member;
import com.bit.shoppingmall.app.exception.member.MemberEntityNotFoundException;
import com.bit.shoppingmall.app.exception.order.OrderMemberNotEnoughMoneyException;
import com.bit.shoppingmall.app.exception.order.OrderMemberUpdateMoneyException;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class OrderMemberManager {

  private final MemberMapper memberMapper;

  public Member determineMember(Long memberId) {
    return memberMapper.select(memberId).orElseThrow(MemberEntityNotFoundException::new);
  }

  public OrderMemberDetail determineOrderMemberDetail(Long memberId) {
    return memberMapper
        .selectAddressAndCouponById(memberId)
        .orElseThrow(MemberEntityNotFoundException::new);
  }

  public void validateEnoughMoney(Long money, Long price) {
    if (money - price < 0) {
      throw new OrderMemberNotEnoughMoneyException();
    }
  }

  public void updateMemberMoney(Member member, Long money) {
    member.updateMoney(money);
    if (memberMapper.update(member) == 0) {
      throw new OrderMemberUpdateMoneyException();
    }
  }
}
