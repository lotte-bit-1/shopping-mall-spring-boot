package com.bit.shoppingmall.app.dto.product.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductSearchByKeyword {

    private Long id;
    private String name;
}
