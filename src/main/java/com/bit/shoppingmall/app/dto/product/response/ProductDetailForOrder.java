package com.bit.shoppingmall.app.dto.product.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductDetailForOrder {

    private Long id;
    private String name;
    private Long quantity;
    private String url;
    private Long price;
}
