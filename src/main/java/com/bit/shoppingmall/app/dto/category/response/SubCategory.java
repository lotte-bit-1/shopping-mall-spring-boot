package com.bit.shoppingmall.app.dto.category.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SubCategory {
    private Long low;
    private Long middle;
    private Long high;
}
