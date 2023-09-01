package com.bit.shoppingmall.app.service.service;

import com.bit.shoppingmall.app.dto.cart.CartAndProductDto;
import com.bit.shoppingmall.app.dto.order.request.OrderCartCreateDto;
import com.bit.shoppingmall.app.dto.order.request.OrderCreateDto;
import com.bit.shoppingmall.app.dto.order.response.ProductOrderDetailDto;
import com.bit.shoppingmall.app.dto.order.response.ProductOrderDto;
import com.bit.shoppingmall.app.entity.*;
import com.bit.shoppingmall.app.enums.CouponPolicy;
import com.bit.shoppingmall.app.enums.CouponStatus;
import com.bit.shoppingmall.app.enums.DeliveryStatus;
import com.bit.shoppingmall.app.enums.OrderStatus;
import com.bit.shoppingmall.app.exception.order.OrderMemberNotEnoughMoneyException;
import com.bit.shoppingmall.app.exception.order.OrderProductNotEnoughStockQuantityException;
import com.bit.shoppingmall.app.mapper.*;
import com.bit.shoppingmall.app.service.order.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    private Long memberId;

    private Long couponId;

    private final Product[] products = new Product[5];

    @BeforeEach
    void beforeEach() {
        Member member = Member.builder().email("test@naver.com").password("test123").name("상셀").money(10000000L).build();
        memberMapper.insert(member);
        memberId = member.getId();

        Coupon coupon = Coupon.builder().memberId(member.getId()).name("10000원 할인 쿠폰").discountPolicy(CouponPolicy.CASH.name())
                .discountValue(10000).status(CouponStatus.UNUSED.name()).build();
        couponMapper.insert(coupon);
        couponId = coupon.getId();

        Category category = Category.builder().name("카테고리 1").level(1).build();
        categoryMapper.insert(category);

        for (int i = 0; i < 5; i++) {
            Product product = Product.builder().categoryId(category.getId())
                    .name("물건" + i).description("물건 상세정보").price(10000L).quantity(10L).code("CODE" + i).build();
            productMapper.insert(product);
            products[i] = product;

            ProductImage productImage = ProductImage.builder()
                    .productId(product.getId()).url("https://example.com").isThumbnail(true).build();
            productImageMapper.insert(productImage);

            Cart cart = Cart.builder().memberId(memberId).productId(products[i].getId()).productQuantity(1L).build();
            cartMapper.insert(cart);
        }
    }

    @Test
    @DisplayName("상품 주문 바로 구매(쿠폰 미적용) - 정상 처리")
    void createOrderWithoutCoupon() throws Exception {
        // given
        OrderCreateDto orderCreateDto =
                OrderCreateDto.builder()
                        .memberId(memberId)
                        .roadName("상품 주문 테스트 도로명 주소")
                        .addrDetail("상품 주문 테스트")
                        .zipCode("상품 주문 테스트")
                        .productId(products[0].getId())
                        .price(products[0].getPrice())
                        .quantity(1L)
                        .couponId(null)
                        .totalPrice(products[0].getPrice())
                        .build();

        // when
        Order order = orderService.createOrder(orderCreateDto);

        // then
        Optional<Order> findOrder = orderMapper.selectById(order.getId());
        assertThat(findOrder.isPresent()).isTrue();
        assertThat(order.getId()).isSameAs(findOrder.get().getId());
    }

    @Test
    @DisplayName("상품 주문 바로 구매 - 비정상 처리(상품 재고 부족)")
    void createOrderWithoutCouponEx1() throws Exception {
        // given
        OrderCreateDto orderCreateDto =
                OrderCreateDto.builder()
                        .memberId(memberId)
                        .roadName("상품 주문 테스트 도로명 주소")
                        .addrDetail("상품 주문 테스트")
                        .zipCode("상품 주문 테스트")
                        .productId(products[0].getId())
                        .price(products[0].getPrice())
                        .quantity(11L)
                        .couponId(null)
                        .totalPrice(products[0].getPrice())
                        .build();

        // then
        assertThatThrownBy(() -> orderService.createOrder(orderCreateDto))
                .isInstanceOf(OrderProductNotEnoughStockQuantityException.class);
    }

    @Test
    @DisplayName("상품 주문 바로 구매 - 비정상 처리(회원 잔고 부족)")
    void createOrderWithoutCouponEx2() throws Exception {
        // given
        OrderCreateDto orderCreateDto =
                OrderCreateDto.builder()
                        .memberId(memberId)
                        .roadName("상품 주문 테스트 도로명 주소")
                        .addrDetail("상품 주문 테스트")
                        .zipCode("상품 주문 테스트")
                        .productId(products[0].getId())
                        .price(1000000000L)
                        .quantity(1L)
                        .couponId(null)
                        .totalPrice(1000000000L)
                        .build();

        // then
        assertThatThrownBy(() -> orderService.createOrder(orderCreateDto))
                .isInstanceOf(OrderMemberNotEnoughMoneyException.class);
    }

    @Test
    @DisplayName("상품 주문 바로 구매(쿠폰 적용) - 정상 처리")
    void createOrderWithCoupon() throws Exception {
        // given
        OrderCreateDto orderCreateDto =
                OrderCreateDto.builder()
                        .memberId(memberId)
                        .roadName("상품 주문 테스트 도로명 주소")
                        .addrDetail("상품 주문 테스트")
                        .zipCode("상품 주문 테스트")
                        .productId(products[0].getId())
                        .price(products[0].getPrice())
                        .quantity(1L)
                        .couponId(couponId)
                        .totalPrice(products[0].getPrice() - 5000L)
                        .build();

        // when
        Order order = orderService.createOrder(orderCreateDto);

        // then
        Optional<Order> findOrder = orderMapper.selectById(order.getId());
        assertThat(findOrder.isPresent()).isTrue();
        assertThat(order.getId()).isSameAs(findOrder.get().getId());
        assertThat(findOrder.get().getCouponId()).isSameAs(couponId);
    }

    @Test
    @DisplayName("장바구니 상품 주문(쿠폰 미적용) - 정상 처리")
    void createCartOrderWithoutCoupon() throws Exception {
        // given
        final List<OrderCartCreateDto.ProductDto> productDtos = new ArrayList<>();
        Long totalPrice = 0L;
        for (int i = 0; i < 5; i++) {
            productDtos.add(new OrderCartCreateDto.ProductDto(products[i].getId(), products[i].getPrice(), 1L));
            totalPrice += products[i].getPrice();
        }
        OrderCartCreateDto orderCartCreateDto = OrderCartCreateDto.builder()
                .memberId(memberId)
                .roadName("상품 주문 테스트 도로명 주소")
                .addrDetail("상품 주문 테스트")
                .zipCode("상품 주문 테스트")
                .products(productDtos)
                .couponId(null)
                .totalPrice(totalPrice)
                .build();

        // when
        Order order = orderService.createCartOrder(orderCartCreateDto);

        // then
        Optional<Order> findOrder = orderMapper.selectById(order.getId());
        assertThat(findOrder.isPresent()).isTrue();
        assertThat(order.getId()).isSameAs(findOrder.get().getId());
    }

    @Test
    @DisplayName("상품 주문 취소 - 정상 처리")
    void cancelOrderWithoutCoupon() throws Exception {
        // given
        OrderCreateDto orderCreateDto =
                OrderCreateDto.builder()
                        .memberId(memberId)
                        .roadName("상품 주문 테스트 도로명 주소")
                        .addrDetail("상품 주문 테스트")
                        .zipCode("상품 주문 테스트")
                        .productId(products[0].getId())
                        .price(products[0].getPrice())
                        .quantity(1L)
                        .couponId(null)
                        .totalPrice(products[0].getPrice())
                        .build();
        Order order = orderService.createOrder(orderCreateDto);

        // when
        orderService.cancelOrder(order.getId());

        // then
        Optional<Order> findOrder = orderMapper.selectById(order.getId());
        Optional<Delivery> findDelivery = deliveryMapper.selectByOrderId(order.getId());
        assertThat(findOrder.isPresent()).isTrue();
        assertThat(findOrder.get().getStatus()).isEqualTo(OrderStatus.CANCELED.name());
        assertThat(findDelivery.isPresent()).isTrue();
        assertThat(findDelivery.get().getStatus()).isEqualTo(DeliveryStatus.CANCELED.name());
    }

    @Test
    @DisplayName("회원 id로 주문 모두 조회 테스트 - 정상 처리")
    void getProductOrdersForMemberCurrentYear() throws Exception {
        // given
        OrderCreateDto orderCreateDto =
                OrderCreateDto.builder()
                        .memberId(memberId)
                        .roadName("상품 주문 테스트 도로명 주소")
                        .addrDetail("상품 주문 테스트")
                        .zipCode("상품 주문 테스트")
                        .productId(products[0].getId())
                        .price(products[0].getPrice())
                        .quantity(1L)
                        .couponId(null)
                        .totalPrice(products[0].getPrice())
                        .build();
        Order order = orderService.createOrder(orderCreateDto);

        // when
        List<ProductOrderDto> productOrderDtos = orderService.getProductOrdersForMemberCurrentYear(memberId);

        // then
        assertThat(productOrderDtos.size()).isSameAs(1);
        assertThat(productOrderDtos.get(0).getOrderId()).isSameAs(order.getId());
    }

    @Test
    @DisplayName("주문 id, 회원 id로 주문 조회 테스트 - 정상 처리")
    void getOrderDetailsForMemberAndOrderId() throws Exception {
        // given
        OrderCreateDto orderCreateDto =
                OrderCreateDto.builder()
                        .memberId(memberId)
                        .roadName("상품 주문 테스트 도로명 주소")
                        .addrDetail("상품 주문 테스트")
                        .zipCode("상품 주문 테스트")
                        .productId(products[0].getId())
                        .price(products[0].getPrice())
                        .quantity(1L)
                        .couponId(null)
                        .totalPrice(products[0].getPrice())
                        .build();
        Order order = orderService.createOrder(orderCreateDto);

        // when
        ProductOrderDetailDto productOrderDetailDto = orderService
                .getOrderDetailsForMemberAndOrderId(order.getId(), memberId);

        // then
        assertThat(productOrderDetailDto.getOrderId()).isSameAs(order.getId());
    }
}
