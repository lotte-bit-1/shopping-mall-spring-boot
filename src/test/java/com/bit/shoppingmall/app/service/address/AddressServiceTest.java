package com.bit.shoppingmall.app.service.address;

import com.bit.shoppingmall.app.dto.address.response.AddressForMyPage;
import com.bit.shoppingmall.app.entity.Member;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AddressServiceTest {
  @Autowired AddressService addressService;
  @Autowired MemberMapper memberMapper;

  @BeforeEach
  void beforeEach() {
    memberMapper.insert(
        Member.builder().name("member1").email("email").password("1234").money(1000L).build());
    memberMapper.insert(
        Member.builder().name("member").email("emafgil").password("1234").money(1000L).build());
  }

  @Test
  @DisplayName("전체 주소 조회")
  void selectAllAddress() {
    AddressForMyPage addressListByMemberId = addressService.getAddressListByMemberId(1L);
    Assertions.assertThat(addressListByMemberId.getDefaultAddress()).isEqualTo(0);
  }
}
