package com.bit.shoppingmall.app.dto.product.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductSearchBySubCategory {
    private Long id;
    private String name;
    private Long price;
    private String url;
    private Boolean isLiked;
}
