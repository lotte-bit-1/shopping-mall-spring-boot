package com.bit.shoppingmall.app.dto.member.response;

import com.bit.shoppingmall.app.dto.response.AddressDetail;
import com.bit.shoppingmall.app.dto.response.CouponDetail;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderMemberDetail {

    private Long id;
    private String email;
    private String name;
    private Long money;
    private AddressDetail address;
    private List<CouponDetail> coupons;
}
