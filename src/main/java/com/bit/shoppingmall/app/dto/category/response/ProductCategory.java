package com.bit.shoppingmall.app.dto.category.response;

import lombok.*;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductCategory {

    private Map<Integer, String> categoryList;
}
