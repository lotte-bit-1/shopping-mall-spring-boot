package com.bit.shoppingmall.app.dto.order.form;

import com.bit.shoppingmall.app.dto.cart.CartAndProductDto;
import com.bit.shoppingmall.app.dto.member.response.OrderMemberDetail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class OrderCartCreateForm {

    private String memberName;
    private List<ProductDto> products;
    private AddressDto defaultAddress;
    private List<CouponDto> coupons;

    public static OrderCartCreateForm of(
            OrderMemberDetail orderMemberDetail, List<CartAndProductDto> cartAndProductDtos) {
        return OrderCartCreateForm.builder()
                .memberName(orderMemberDetail.getName())
                .products(
                        cartAndProductDtos.stream()
                                .map(
                                        cp ->
                                                new ProductDto(
                                                        cp.getProductId(),
                                                        cp.getName(),
                                                        cp.getPrice(),
                                                        cp.getCartProductQuantity()))
                                .collect(Collectors.toList()))
                .defaultAddress(
                        new AddressDto(
                                orderMemberDetail.getAddress() == null
                                        ? null
                                        : orderMemberDetail.getAddress().getRoadName(),
                                orderMemberDetail.getAddress() == null
                                        ? null
                                        : orderMemberDetail.getAddress().getAddrDetail(),
                                orderMemberDetail.getAddress() == null
                                        ? null
                                        : orderMemberDetail.getAddress().getZipCode()))
                .coupons(
                        orderMemberDetail.getCoupons().stream()
                                .map(
                                        c ->
                                                new CouponDto(
                                                        c.getId(),
                                                        c.getName(),
                                                        c.getDiscountPolicy(),
                                                        c.getDiscountValue(),
                                                        c.getStatus()))
                                .collect(Collectors.toList()))
                .build();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddressDto {

        private String roadName;
        private String addrDetail;
        private String zipCode;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CouponDto {

        private Long id;
        private String name;
        private String discountPolicy;
        private Integer discountValue;
        private String status;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProductDto {

        private Long id;
        private String name;
        private Long price;
        private Long quantity;
    }
}
