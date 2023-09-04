package com.bit.shoppingmall.app.dto.order.request;

import com.bit.shoppingmall.app.dto.cart.CartAndProductDto;
import com.bit.shoppingmall.app.entity.Delivery;
import com.bit.shoppingmall.app.entity.Order;
import com.bit.shoppingmall.app.entity.Payment;
import com.bit.shoppingmall.app.entity.ProductOrder;
import com.bit.shoppingmall.app.enums.DeliveryStatus;
import com.bit.shoppingmall.app.enums.OrderStatus;
import com.bit.shoppingmall.app.enums.PaymentType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCartCreateDto {

  @JsonIgnore private Long memberId;
  private Long couponId;
  @NotBlank private String roadName;
  @NotBlank private String addrDetail;
  @NotBlank private String zipCode;
  private List<ProductDto> products;
  @NotNull private Long totalPrice;

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  public void setCouponId(Long couponId) {
    this.couponId = couponId;
  }

  public void setProducts(List<CartAndProductDto> cartAndProductDtos) {
    products =
        cartAndProductDtos.stream()
            .map(
                cp -> new ProductDto(cp.getProductId(), cp.getPrice(), cp.getCartProductQuantity()))
            .collect(Collectors.toList());
  }

  public Order toOrderEntity() {
    return Order.builder()
        .memberId(memberId)
        .couponId(couponId)
        .status(OrderStatus.PENDING.name())
        .build();
  }

  public List<ProductOrder> toProductOrderEntities(Long orderId) {
    return products.stream()
        .map(
            p ->
                ProductOrder.builder()
                    .orderId(orderId)
                    .productId(p.productId)
                    .price(p.price)
                    .quantity(p.quantity)
                    .build())
        .collect(Collectors.toList());
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

  @Getter
  @AllArgsConstructor
  public static class ProductDto implements Serializable {

    private Long productId;
    private Long price;
    private Long quantity;
  }
}
