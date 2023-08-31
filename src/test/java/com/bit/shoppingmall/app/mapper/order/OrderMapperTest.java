package com.bit.shoppingmall.app.mapper.order;

import com.bit.shoppingmall.app.dto.order.response.ProductOrderDetailDto;
import com.bit.shoppingmall.app.dto.order.response.ProductOrderDto;
import com.bit.shoppingmall.app.entity.*;
import com.bit.shoppingmall.app.enums.DeliveryStatus;
import com.bit.shoppingmall.app.enums.OrderStatus;
import com.bit.shoppingmall.app.enums.PaymentType;
import com.bit.shoppingmall.app.mapper.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private MemberMapper memberMapper;

    private static Long memberId;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @BeforeEach
    void beforeEach() {
        Member member = Member.builder().email("test@naver.com").password("test123").name("상셀").money(1000000L).build();
        memberMapper.insert(member);
        memberId = member.getId();
    }

    @Test
    @DisplayName("주문 생성 - 정상 처리")
    void insert() throws Exception {
        // given
        Order order = Order.builder().memberId(memberId).status(OrderStatus.PENDING.name()).build();

        // when
        int insertedRow = orderMapper.insert(order);

        // then
        assertThat(insertedRow).isSameAs(1);
        assertThat(order.getId()).isNotNull();
    }

    @Test
    @DisplayName("주문 생성 - 존재하지 않는 사용자")
    void insertEx1() throws Exception {
        // given
        Long invalidMemberId = 10000L;
        Order order = Order.builder().memberId(invalidMemberId).status(OrderStatus.PENDING.name()).build();

        // when, then
        assertThatThrownBy(() -> orderMapper.insert(order)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("주문 단건 조회 - 정상 처리")
    void selectById() throws Exception {
        // given
        Order order = Order.builder().memberId(memberId).status(OrderStatus.PENDING.name()).build();
        orderMapper.insert(order);

        // when
        Optional<Order> findOrder = orderMapper.selectById(order.getId());

        // then
        assertThat(findOrder.isPresent()).isTrue();
        assertThat(findOrder.get().getId()).isSameAs(order.getId());
    }

    @Test
    @DisplayName("주문 단건 조회 - 존재하지 않는 주문")
    void selectByIdReturnEmpty() throws Exception {
        // given
        Order order = Order.builder().memberId(memberId).status(OrderStatus.PENDING.name()).build();
        orderMapper.insert(order);

        // when
        Long invalidOrderId = 10000L;
        Optional<Order> findOrder = orderMapper.selectById(invalidOrderId);

        // then
        assertThat(findOrder.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("주문 모두 조회 - 정상 처리")
    void selectProductOrdersForMemberCurrentYear() throws Exception {
        // given
        Category category = Category.builder().name("카테고리 1").level(1).build();
        categoryMapper.insert(category);

        final Product[] products = new Product[5];
        for (int i = 0; i < 5; i++) {
            Product product = Product.builder().categoryId(category.getId()).name("물건" + i).description("물건 상세정보").price(10000L).quantity(10L).code("CODE" + i).build();
            productMapper.insert(product);
            products[i] = product;

            ProductImage productImage = ProductImage.builder().productId(product.getId()).url("https://example.com").isThumbnail(true).build();
            productImageMapper.insert(productImage);
        }

        Order order = Order.builder().memberId(memberId).status(OrderStatus.PENDING.name()).build();
        orderMapper.insert(order);

        for (int i = 0; i < 5; i++) {
            ProductOrder productOrder = ProductOrder.builder()
                    .orderId(order.getId()).productId(products[i].getId()).price(products[i].getPrice()).quantity(1L).build();
            productOrderMapper.insert(productOrder);
        }

        Delivery delivery = Delivery.builder().orderId(order.getId())
                .roadName("road").addrDetail("detail").zipCode("12345").status(DeliveryStatus.PENDING.name()).build();
        deliveryMapper.insert(delivery);

        final Long actualAmount = 50000L;
        Payment payment = Payment.builder().orderId(order.getId()).type(PaymentType.CASH.name()).actualAmount(actualAmount).build();
        paymentMapper.insert(payment);

        // when
        List<ProductOrderDto> productOrderDtos = orderMapper.selectProductOrdersForMemberCurrentYear(memberId);

        // then
        assertThat(productOrderDtos.size()).isSameAs(1);
        assertThat(productOrderDtos.get(0).getOrderId()).isSameAs(order.getId());
        assertThat(productOrderDtos.get(0).getProducts().size()).isSameAs(5);

    }

    @Test
    @DisplayName("주문 단건 상세 조회 - 정상 처리")
    void selectOrderDetailsForMemberAndOrderId() throws Exception {
        // given
        Category category = Category.builder().name("카테고리 1").level(1).build();
        categoryMapper.insert(category);

        final Product[] products = new Product[5];
        for (int i = 0; i < 5; i++) {
            Product product = Product.builder().categoryId(category.getId()).name("물건" + i).description("물건 상세정보").price(10000L).quantity(10L).code("CODE" + i).build();
            productMapper.insert(product);
            products[i] = product;

            ProductImage productImage = ProductImage.builder().productId(product.getId()).url("https://example.com").isThumbnail(true).build();
            productImageMapper.insert(productImage);
        }

        Order order = Order.builder().memberId(memberId).status(OrderStatus.PENDING.name()).build();
        orderMapper.insert(order);

        for (int i = 0; i < 5; i++) {
            ProductOrder productOrder = ProductOrder.builder()
                    .orderId(order.getId()).productId(products[i].getId()).price(products[i].getPrice()).quantity(1L).build();
            productOrderMapper.insert(productOrder);
        }

        Delivery delivery = Delivery.builder().orderId(order.getId())
                .roadName("road").addrDetail("detail").zipCode("12345").status(DeliveryStatus.PENDING.name()).build();
        deliveryMapper.insert(delivery);

        final Long actualAmount = 50000L;
        Payment payment = Payment.builder().orderId(order.getId()).type(PaymentType.CASH.name()).actualAmount(actualAmount).build();
        paymentMapper.insert(payment);

        // when
        final Map<String, Long> orderIdAndMemberIdParameterMap = new HashMap<>();
        orderIdAndMemberIdParameterMap.put("memberId", memberId);
        orderIdAndMemberIdParameterMap.put("orderId", order.getId());
        Optional<ProductOrderDetailDto> productOrderDetailDto = orderMapper
                .selectOrderDetailsForMemberAndOrderId(orderIdAndMemberIdParameterMap);

        // then
        assertThat(productOrderDetailDto.isPresent()).isTrue();
        assertThat(productOrderDetailDto.get().getOrderId()).isSameAs(order.getId());
        assertThat(productOrderDetailDto.get().getProducts().size()).isSameAs(5);
        assertThat(productOrderDetailDto.get().getDelivery()).isNotNull();
        assertThat(productOrderDetailDto.get().getPayment()).isNotNull();
    }
}
