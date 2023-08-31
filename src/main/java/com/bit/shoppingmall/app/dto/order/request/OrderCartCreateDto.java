package com.bit.shoppingmall.app.dto.order.request;

import com.bit.shoppingmall.app.dto.cart.CartAndProductDto;
import com.bit.shoppingmall.app.entity.Delivery;
import com.bit.shoppingmall.app.entity.Order;
import com.bit.shoppingmall.app.entity.Payment;
import com.bit.shoppingmall.app.entity.ProductOrder;
import com.bit.shoppingmall.app.enums.DeliveryStatus;
import com.bit.shoppingmall.app.enums.OrderStatus;
import com.bit.shoppingmall.app.enums.PaymentType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCartCreateDto {

  private Long memberId;
  private Long couponId;
  private String roadName;
  private String addrDetail;
  private String zipCode;
  private List<ProductDto> products;
  private Long totalPrice;

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

  public Payment toPaymentEntity(Long orderId) {
    return Payment.builder()
        .orderId(orderId)
        .type(PaymentType.CASH.name())
        .actualAmount(totalPrice)
        .build();
  }

  @Getter
  @AllArgsConstructor
  public static class ProductDto {

    private Long productId;
    private Long price;
    private Long quantity;
  }
}
