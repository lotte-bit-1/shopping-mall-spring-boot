package com.bit.shoppingmall.app.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Category {

    private Long id;
    private Long parentId;
    @NonNull
    private String name;
    @NonNull
    private Integer level;
}
