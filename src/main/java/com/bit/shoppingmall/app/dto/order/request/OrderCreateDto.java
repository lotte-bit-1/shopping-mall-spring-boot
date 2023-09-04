package com.bit.shoppingmall.app.dto.order.request;

import com.bit.shoppingmall.app.entity.Delivery;
import com.bit.shoppingmall.app.entity.Order;
import com.bit.shoppingmall.app.entity.Payment;
import com.bit.shoppingmall.app.entity.ProductOrder;
import com.bit.shoppingmall.app.enums.DeliveryStatus;
import com.bit.shoppingmall.app.enums.OrderStatus;
import com.bit.shoppingmall.app.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateDto {

  @JsonIgnore private Long memberId;
  private Long couponId;
  @NotBlank private String roadName;
  @NotBlank private String addrDetail;
  @NotBlank private String zipCode;
  @NotNull private Long productId;
  @NotNull private Long price;
  @NotNull private Long quantity;
  @NotNull private Long totalPrice;

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  public void setCouponId(Long couponId) {
    this.couponId = couponId;
  }

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

  public Payment toPaymentEntity(Long orderId, Long discountPrice) {
    return Payment.builder()
        .orderId(orderId)
        .type(PaymentType.CASH.name())
        .actualAmount(totalPrice - discountPrice)
        .build();
  }
}
