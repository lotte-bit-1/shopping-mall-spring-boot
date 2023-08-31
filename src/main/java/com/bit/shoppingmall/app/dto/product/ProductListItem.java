package com.bit.shoppingmall.app.dto.product;

import com.bit.shoppingmall.app.entity.BaseEntity;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductListItem extends BaseEntity {
    private Long id;
    private String name;
    private Long price;
    private String url;
    private Boolean isLiked;
}
