package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.dto.member.response.OrderMemberDetail;
import com.bit.shoppingmall.app.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberMapperTest {

    @Autowired
    MemberMapper memberDao;

    @Test
    @DisplayName("주문 시 멤버와 관련된 기본 주소지 정보, 쿠폰 정보(리스트)를 같이 조회 한다.")
    void select() {
        // given
        Member member = createMember();
        memberDao.insert(member);

        // when
        OrderMemberDetail orderMemberDetail = memberDao.selectAddressAndCouponById(member.getId()).get();

        // then
        assertThat(orderMemberDetail.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    @DisplayName("입력받은 이메일 데이터베이스에 존재하는지 검사한다. 존재하면 1을 반환한다.")
    void countByEmail_result_1() {
        // given
        Member member = createMember();
        memberDao.insert(member);
        int expectedResult = 1;

        // when
        int result = memberDao.countByEmail(member.getEmail());

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("입력받은 이메일 데이터베이스에 존재하는지 검사한다. 존재 하지 않으면 0을 반환한다.")
    void countByEmail_result_0() {
        // given
        Member member = createMember();
        memberDao.insert(member);
        int expectedResult = 0;

        // when
        int result = memberDao.countByEmail("user02@naver.com");

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Member createMember() {
        return Member.builder().email("user01@naver.com").password("test").name("테스트").build();
    }

}