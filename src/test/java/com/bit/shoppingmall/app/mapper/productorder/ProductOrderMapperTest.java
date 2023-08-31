package com.bit.shoppingmall.app.mapper.productorder;

import com.bit.shoppingmall.app.entity.Member;
import com.bit.shoppingmall.app.entity.Order;
import com.bit.shoppingmall.app.entity.ProductOrder;
import com.bit.shoppingmall.app.enums.OrderStatus;
import com.bit.shoppingmall.app.mapper.MemberMapper;
import com.bit.shoppingmall.app.mapper.OrderMapper;
import com.bit.shoppingmall.app.mapper.ProductOrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductOrderMapperTest {

    @Autowired
    private ProductOrderMapper productOrderMapper;

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

//    @Test
//    @DisplayName("주문 생성 - 정상 처리")
//    void insert() throws Exception {
//        // given
//        Order order = Order.builder().memberId(memberId).status(OrderStatus.PENDING.name()).build();
//        orderMapper.insert(order);
//
//        ProductOrder.builder().orderId().build();
//
//        // when
//        productOrderMapper.insert()
//
//        // then
//        assertThat(insertedRow).isSameAs(1);
//        assertThat(order.getId()).isNotNull();
//    }
}
