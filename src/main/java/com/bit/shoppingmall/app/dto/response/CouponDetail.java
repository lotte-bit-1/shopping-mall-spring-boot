package com.bit.shoppingmall.app.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponDetail {

    private Long id;
    private String name;
    private String discountPolicy;
    private Integer discountValue;
    private String status;
}
