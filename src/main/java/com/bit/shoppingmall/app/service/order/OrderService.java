package com.bit.shoppingmall.app.service.order;

import com.bit.shoppingmall.app.dto.cart.CartAndProductDto;
import com.bit.shoppingmall.app.dto.member.response.OrderMemberDetail;
import com.bit.shoppingmall.app.dto.order.form.OrderCartCreateForm;
import com.bit.shoppingmall.app.dto.order.form.OrderCreateForm;
import com.bit.shoppingmall.app.dto.order.request.OrderCartCreateDto;
import com.bit.shoppingmall.app.dto.order.request.OrderCreateDto;
import com.bit.shoppingmall.app.dto.order.response.ProductOrderDetailDto;
import com.bit.shoppingmall.app.dto.order.response.ProductOrderDto;
import com.bit.shoppingmall.app.dto.product.response.ProductDetailForOrder;
import com.bit.shoppingmall.app.entity.*;
import com.bit.shoppingmall.app.enums.CouponStatus;
import com.bit.shoppingmall.app.enums.DeliveryStatus;
import com.bit.shoppingmall.app.enums.OrderStatus;
import com.bit.shoppingmall.app.service.order.interfaces.OrderCartCreateService;
import com.bit.shoppingmall.app.service.order.interfaces.OrderCreateService;
import com.bit.shoppingmall.app.service.order.interfaces.OrderDeleteService;
import com.bit.shoppingmall.app.service.order.interfaces.OrderReadService;
import com.bit.shoppingmall.app.service.order.manager.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService
    implements OrderCreateService, OrderCartCreateService, OrderReadService, OrderDeleteService {

  private final OrderManager orderManager;
  private final OrderProductManager orderProductManager;
  private final OrderMemberManager orderMemberManager;
  private final OrderCouponManager orderCouponManager;
  private final OrderProductOrderManager orderProductOrderManager;
  private final OrderDeliveryManager orderDeliveryManager;
  private final OrderPaymentManager orderPaymentManager;
  private final OrderCartManager orderCartManager;

  /* 상품 주문 폼 */
  @Override
  public OrderCreateForm getCreateOrderForm(Long memberId, Long productId) {
    ProductDetailForOrder productDetail =
            orderProductManager.determineProductDetailForOrder(productId);
    OrderMemberDetail orderMemberDetail =
            orderMemberManager.determineOrderMemberDetail(memberId);

    return OrderCreateForm.of(orderMemberDetail, productDetail);
  }

  /* 상품 주문 */
  @Transactional
  @Override
  public Order createOrder(OrderCreateDto orderCreateDto) throws Exception {
    /* 상품 재고 확인 1. 없다면 구매 불가 2. 있다면 재고 차감 */
    Product product =
            orderProductManager.determineProduct(orderCreateDto.getProductId());
    orderProductManager.validateEnoughStockQuantity(
            product.getQuantity(), orderCreateDto.getQuantity());
    orderProductManager.updateStockQuantity(
            product, product.getQuantity() - orderCreateDto.getQuantity());

    /* 회원이 쿠폰을 썼는지 확인 1. 쿠폰을 적용했다면 회원의 쿠폰 정보 '사용됨' 상태로 바꿈 2. 쿠폰을 적용하지 않았다면 패스 */
    Long couponId = orderCreateDto.getCouponId();
    if (orderCouponManager.isCouponUsed(couponId)) {
      Coupon coupon = orderCouponManager.determineCoupon(couponId);
      orderCouponManager.updateCouponStatus(coupon, CouponStatus.USED);
    }

    /* 회원의 잔액 확인 1. 총 상품 가격보다 잔액이 적다면 구매 불가 2. 잔액이 충분하다면 회원의 잔액에서 차감 */
    Member member = orderMemberManager.determineMember(orderCreateDto.getMemberId());
    orderMemberManager.validateEnoughMoney(member.getMoney(), orderCreateDto.getTotalPrice());
    orderMemberManager.updateMemberMoney(
            member, member.getMoney() - orderCreateDto.getTotalPrice());

    /* 상품 주문 Order, ProductOrder, Payment, Delivery 생성 */
    Order order = orderCreateDto.toOrderEntity();
    orderManager.createOrder(order);

    ProductOrder productOrder = orderCreateDto.toProductOrderEntity(order.getId());
    orderProductOrderManager.createProductOrder(productOrder);

    Delivery delivery = orderCreateDto.toDeliveryEntity(order.getId());
    orderDeliveryManager.createDelivery(delivery);

    Payment payment = orderCreateDto.toPaymentEntity(order.getId());
    orderPaymentManager.createPayment(payment);

    return order;
  }

  /* 장바구니 상품 주문 폼 */
  @Override
  public OrderCartCreateForm getCreateCartOrderForm(Long memberId) {
    /* 회원 정보 조회 */
    OrderMemberDetail orderMemberDetail =
            orderMemberManager.determineOrderMemberDetail(memberId);
    /* 회원으로 장바구니에 들어있는 상품들 모두 조회 */
    List<CartAndProductDto> cartAndProductDtos =
            orderCartManager.determineCartAndProductDtos(memberId);
    cartAndProductDtos.forEach(
            cp ->
                    orderProductManager.validateEnoughStockQuantity(
                            cp.getProductQuantity(), cp.getCartProductQuantity()));

    return OrderCartCreateForm.of(orderMemberDetail, cartAndProductDtos);
  }

  /* 장바구니 상품 주문 */
  @Transactional
  @Override
  public Order createCartOrder(OrderCartCreateDto orderCartCreateDto) throws Exception {
    List<CartAndProductDto> cartAndProductDtos =
            orderCartManager.determineCartAndProductDtos(orderCartCreateDto.getMemberId());
    orderCartCreateDto.setProducts(cartAndProductDtos);
    /* 상품 재고 확인 1. 없다면 구매 불가 2. 있다면 재고 차감 */
    List<ProductAndMemberCompositeKey> productAndMemberCompositeKeys = new ArrayList<>();
    orderCartCreateDto
            .getProducts()
            .forEach(
                    p -> {
                      try {
                        Product product = orderProductManager.determineProduct(p.getProductId());
                        orderProductManager.validateEnoughStockQuantity(
                                product.getQuantity(), p.getQuantity());
                        orderProductManager.updateStockQuantity(
                                product, product.getQuantity() - p.getQuantity());

                        productAndMemberCompositeKeys.add(
                                ProductAndMemberCompositeKey.builder()
                                        .memberId(orderCartCreateDto.getMemberId())
                                        .productId(product.getId())
                                        .build());

                      } catch (Exception e) {
                        throw new RuntimeException(e);
                      }
                    });

    /* 장바구니 벌크 삭제 */
    orderCartManager.deleteAll(productAndMemberCompositeKeys);

    /* 회원이 쿠폰을 썼는지 확인 1. 쿠폰을 적용했다면 회원의 쿠폰 정보 '사용됨' 상태로 바꿈 2. 쿠폰을 적용하지 않았다면 패스 */
    Long couponId = orderCartCreateDto.getCouponId();
    if (orderCouponManager.isCouponUsed(couponId)) {
      Coupon coupon = orderCouponManager.determineCoupon(couponId);
      orderCouponManager.updateCouponStatus(coupon, CouponStatus.USED);
    }

    /* 회원의 잔액 확인 1. 총 상품 가격보다 잔액이 적다면 구매 불가 2. 잔액이 충분하다면 회원의 잔액에서 차감 */
    Member member = orderMemberManager.determineMember(orderCartCreateDto.getMemberId());
    orderMemberManager.validateEnoughMoney(member.getMoney(), orderCartCreateDto.getTotalPrice());
    orderMemberManager.updateMemberMoney(
            member, member.getMoney() - orderCartCreateDto.getTotalPrice());

    /* 상품 주문 orders, product_order, payment, delivery 생성 */
    Order order = orderCartCreateDto.toOrderEntity();
    orderManager.createOrder(order);

    List<ProductOrder> productOrders = orderCartCreateDto.toProductOrderEntities(order.getId());
    orderProductOrderManager.createProductOrders(productOrders);

    Delivery delivery = orderCartCreateDto.toDeliveryEntity(order.getId());
    orderDeliveryManager.createDelivery(delivery);

    Payment payment = orderCartCreateDto.toPaymentEntity(order.getId());
    orderPaymentManager.createPayment(payment);

    return order;
  }

  /* 상품 주문 취소 */
  @Transactional
  @Override
  public void cancelOrder(Long orderId) {
    /* 주문 정보 정보를 취소 상태로 바꿈 */
    Order order = orderManager.determineOrder(orderId);
    orderManager.checkAlreadyOrdered(order);
    orderManager.updateOrderStatus(order, OrderStatus.CANCELED);

    /* 배송 상태를 확인 1. 배송중이면 취소 불가 2. 배송중이 아니라면 배송 정보를 취소 상태로 바꾼다 */
    Delivery delivery = orderDeliveryManager.determineDelivery(orderId);
    orderDeliveryManager.checkDeliveryCanceled(delivery);
    orderDeliveryManager.checkDeliveryProcessing(delivery);

    // 배송중이 아니라면 배송 취소상태로 변경
    orderDeliveryManager.updateDeliveryStatus(delivery, DeliveryStatus.CANCELED);

    /* 사용했던 회원의 쿠폰이 있다면 쿠폰의 상태를 UNUSED로 바꿈 */
    Long couponId = order.getCouponId();
    if (orderCouponManager.isCouponUsed(couponId)) {
      Coupon coupon = orderCouponManager.determineCoupon(couponId);
      orderCouponManager.updateCouponStatus(coupon, CouponStatus.UNUSED);
    }

    /* 취소한 상품들에 대한 수량을 증가시킴 */
    List<ProductOrder> productOrders =
            orderProductOrderManager.determineProductOrdersByOrderId(orderId);
    productOrders.forEach(
            po -> {
              try {
                Product product = orderProductManager.determineProduct(po.getProductId());
                orderProductManager.updateStockQuantity(
                        product, product.getQuantity() + po.getQuantity());
              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            });

    /* 회원의 보유 금액을 실제 결제 금액에 비례하여 증가시킴 */
    Payment payment = orderPaymentManager.determinePaymentByOrderId(orderId);
    Member member = orderMemberManager.determineMember(order.getMemberId());
    orderMemberManager.updateMemberMoney(
            member, member.getMoney() + payment.getActualAmount());
  }

  /* 회원의 1년내의 주문 목록들을 최신순으로 조회 */
  @Override
  public List<ProductOrderDto> getProductOrdersForMemberCurrentYear(Long memberId) {
    return orderManager.getProductOrdersForMemberCurrentYear(memberId);
  }

  /* 회원의 상세 주문을 조회 */
  @Override
  public ProductOrderDetailDto getOrderDetailsForMemberAndOrderId(Long orderId, Long memberId) {
    return orderManager.getOrderDetailsForMemberAndOrderId(orderId, memberId);
  }
}
