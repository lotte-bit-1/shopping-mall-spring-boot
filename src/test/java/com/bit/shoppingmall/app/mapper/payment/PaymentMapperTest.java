package com.bit.shoppingmall.app.mapper.payment;

import com.bit.shoppingmall.app.entity.Member;
import com.bit.shoppingmall.app.entity.Order;
import com.bit.shoppingmall.app.entity.Payment;
import com.bit.shoppingmall.app.enums.OrderStatus;
import com.bit.shoppingmall.app.enums.PaymentType;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import com.bit.shoppingmall.app.mapper.OrderMapper;
import com.bit.shoppingmall.app.mapper.PaymentMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PaymentMapperTest {

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrderMapper orderMapper;

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
    @DisplayName("결제 생성 - 정상 처리")
    void insert() throws Exception {
        // given
        Order order = Order.builder().memberId(memberId).status(OrderStatus.PENDING.name()).build();
        orderMapper.insert(order);
        Payment payment = Payment.builder().orderId(order.getId()).type(PaymentType.CASH.getMessage()).actualAmount(100000L).build();

        // when
        int insertedRow = paymentMapper.insert(payment);

        // then
        assertThat(insertedRow).isSameAs(1);
        assertThat(payment.getId()).isNotNull();
    }

    @Test
    @DisplayName("결제 생성 - 존재하지 않는 주문")
    void insertEx1() throws Exception {
        // given
        Long invalidOrderId = 10000L;
        Payment payment = Payment.builder().orderId(invalidOrderId).type(PaymentType.CASH.getMessage()).actualAmount(100000L).build();

        // when, then
        assertThatThrownBy(() -> paymentMapper.insert(payment)).isInstanceOf(DataIntegrityViolationException.class);
    }
}
