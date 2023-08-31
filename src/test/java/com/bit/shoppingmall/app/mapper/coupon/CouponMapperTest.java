package com.bit.shoppingmall.app.mapper.coupon;

import com.bit.shoppingmall.app.entity.Coupon;
import com.bit.shoppingmall.app.entity.Member;
import com.bit.shoppingmall.app.enums.CouponPolicy;
import com.bit.shoppingmall.app.enums.CouponStatus;
import com.bit.shoppingmall.app.mapper.CouponMapper;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CouponMapperTest {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private MemberMapper memberMapper;

    private static Long memberId;

    @BeforeEach
    void beforeEach() {
        Member member = Member.builder().email("test@naver.com").password("test123").name("상셀").money(1000000L).build();
        memberMapper.insert(member);
        memberId = member.getId();
    }

    @Test
    @DisplayName("쿠폰 생성 - 정상 처리")
    void insert() throws Exception {
        // given
        Coupon coupon = Coupon.builder().memberId(memberId).name("10000원 할인 쿠폰")
                .discountPolicy(CouponPolicy.CASH.name()).discountValue(10000).status(CouponStatus.UNUSED.name()).build();

        // when
        int insertedRow = couponMapper.insert(coupon);

        // then
        assertThat(insertedRow).isSameAs(1);
        assertThat(coupon.getId()).isNotNull();
    }

    @Test
    @DisplayName("쿠폰 생성 - 존재하지 않는 사용자")
    void insertEx1() throws Exception {
        // given
        Long invalidMemberId = 10000L;
        Coupon coupon = Coupon.builder().memberId(invalidMemberId).name("10000원 할인 쿠폰")
                .discountPolicy(CouponPolicy.CASH.name()).discountValue(10000).status(CouponStatus.UNUSED.name()).build();

        // when, then
        assertThatThrownBy(() -> couponMapper.insert(coupon)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("쿠폰 조회 - 정상 처리")
    void selectById() throws Exception {
        // given
        Coupon coupon = Coupon.builder().memberId(memberId).name("10000원 할인 쿠폰")
                .discountPolicy(CouponPolicy.CASH.name()).discountValue(10000).status(CouponStatus.UNUSED.name()).build();
        couponMapper.insert(coupon);

        // when
        Optional<Coupon> findCoupon = couponMapper.selectById(coupon.getId());

        // then
        assertThat(findCoupon.isPresent()).isTrue();
        assertThat(findCoupon.get().getId()).isSameAs(coupon.getId());
    }

    @Test
    @DisplayName("쿠폰 상태 변경 - 정상 처리")
    void updateStatus() throws Exception {
        // given
        Coupon coupon = Coupon.builder().memberId(memberId).name("10000원 할인 쿠폰").discountPolicy(CouponPolicy.CASH.name())
                .discountValue(10000).status(CouponStatus.UNUSED.name()).build();
        couponMapper.insert(coupon);

        // when
        coupon.updateStatus(CouponStatus.USED.name());
        int updatedRow = couponMapper.update(coupon);

        // then

        Coupon findCoupon = couponMapper.selectById(coupon.getId()).get();
        assertThat(updatedRow).isSameAs(1);
        assertThat(findCoupon.getStatus()).isEqualTo(CouponStatus.USED.name());
    }
}
