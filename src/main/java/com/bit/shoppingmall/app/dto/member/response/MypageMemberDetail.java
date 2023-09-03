package com.bit.shoppingmall.app.dto.member.response;

import com.bit.shoppingmall.app.dto.response.AddressDetail;
import com.bit.shoppingmall.app.dto.response.CouponDetail;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MypageMemberDetail {
  private Long id;
  private String email;
  private String name;
  private Long money;
  private List<AddressDetail> address;
  private List<CouponDetail> coupons;
}
