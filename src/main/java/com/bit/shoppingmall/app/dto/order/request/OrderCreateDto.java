package com.bit.shoppingmall.app.dto.order.request;

import com.bit.shoppingmall.app.entity.Delivery;
import com.bit.shoppingmall.app.entity.Order;
import com.bit.shoppingmall.app.entity.Payment;
import com.bit.shoppingmall.app.entity.ProductOrder;
import com.bit.shoppingmall.app.enums.DeliveryStatus;
import com.bit.shoppingmall.app.enums.OrderStatus;
import com.bit.shoppingmall.app.enums.PaymentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateDto {

  private Long memberId;
  private Long couponId;
  private String roadName;
  private String addrDetail;
  private String zipCode;
  private Long productId;
  private Long price;
  private Long quantity;
  private Long totalPrice;

  public Order toOrderEntity() {
    return Order.builder()
        .memberId(memberId)
        .couponId(couponId)
        .status(OrderStatus.PENDING.name())
        .build();
  }

  public ProductOrder toProductOrderEntity(Long orderId) {
    return ProductOrder.builder()
        .orderId(orderId)
        .productId(productId)
        .price(price)
        .quantity(quantity)
        .build();
  }

  public Delivery toDeliveryEntity(Long orderId) {
    return Delivery.builder()
        .orderId(orderId)
        .roadName(roadName)
        .addrDetail(addrDetail)
        .zipCode(zipCode)
        .status(DeliveryStatus.PENDING.name())
        .build();
  }

  public Payment toPaymentEntity(Long orderId) {
    return Payment.builder()
        .orderId(orderId)
        .type(PaymentType.CASH.name())
        .actualAmount(totalPrice)
        .build();
  }
}
