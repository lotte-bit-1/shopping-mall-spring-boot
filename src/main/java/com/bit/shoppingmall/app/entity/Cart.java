package com.bit.shoppingmall.app.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @NonNull
    private Long memberId;
    @NonNull
    private Long productId;
    @NonNull
    private Long productQuantity;

    public static Cart cartCompKeyBuilder(
            ProductAndMemberCompositeKey productAndMemberCompositeKey, Long productQuantity) {
        return new Cart(
                productAndMemberCompositeKey.getMemberId(),
                productAndMemberCompositeKey.getProductId(),
                productQuantity);
    }

    public static Cart updateCart(Cart cart, Long requestQuantity) {
        return Cart.cartCompKeyBuilder(
                new ProductAndMemberCompositeKey(cart.getProductId(), cart.getMemberId()), requestQuantity);
    }
}
