package com.bit.shoppingmall.app.dto.product;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailParameter {

    private Long memberId;
    private Long productId;
}
