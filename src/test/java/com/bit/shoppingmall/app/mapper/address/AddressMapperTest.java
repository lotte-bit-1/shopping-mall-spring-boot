package com.bit.shoppingmall.app.mapper.address;

import com.bit.shoppingmall.app.entity.Address;
import com.bit.shoppingmall.app.entity.Member;
import com.bit.shoppingmall.app.mapper.AddressMapper;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import java.util.List;
import java.util.logging.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AddressMapperTest {
  @Autowired AddressMapper addressMapper;
  @Autowired MemberMapper memberMapper;
  Logger log = Logger.getLogger("address");

  @BeforeEach
  void beforeEach() {
    memberMapper.insert(
        Member.builder().name("member1").email("email").password("1234").money(1000L).build());
    memberMapper.insert(
        Member.builder().name("member").email("emafgil").password("1234").money(1000L).build());
  }

  @Test
  @DisplayName("주소지 저장")
  void insertAddress() {
    String addrDetail = "상세 주소";
    String roadName = "도로명 주소 ";
    String zipCode = "12345";
    Long memberId = 1L;

    Address build =
        Address.builder()
            .addrDetail(addrDetail)
            .memberId(memberId)
            .zipCode(zipCode)
            .roadName(roadName)
            .isDefault(true)
            .build();
    int insertedRow = addressMapper.insert(build);

    List<Member> members = memberMapper.selectAll();
    log.info(members.toString());
    List<Address> address = addressMapper.selectAll();
    log.info(address.toString());
  }

  @Test
  @DisplayName("사용자 주소 전체 조회")
  void selectAllAddressByMemberId() {
    String addrDetail = "상세 주소";
    String roadName = "도로명 주소 ";
    String zipCode = "12345";
    Long memberId = 2L;

    Address build =
        Address.builder()
            .addrDetail(addrDetail)
            .memberId(memberId)
            .zipCode(zipCode)
            .roadName(roadName)
            .isDefault(true)
            .build();

    int insertedRow = addressMapper.insert(build);
    List<Address> member2 = (List<Address>) addressMapper.selectDefaultAddress(2L);
    Assertions.assertThat(member2.size()).isEqualTo(1);
  }
}
