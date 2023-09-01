package com.bit.shoppingmall.app.service.order.manager;

import com.bit.shoppingmall.app.entity.Coupon;
import com.bit.shoppingmall.app.enums.CouponStatus;
import com.bit.shoppingmall.app.exception.coupon.CouponEntityNotFoundException;
import com.bit.shoppingmall.app.exception.order.OrderCouponUpdateStatusException;
import com.bit.shoppingmall.app.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCouponManager {

  private final CouponMapper couponMapper;

  public Coupon determineCoupon(Long couponId) {
    return couponMapper.selectById(couponId).orElseThrow(CouponEntityNotFoundException::new);
  }

  public boolean isCouponUsed(Long couponId) {
    return couponId != null;
  }

  public void updateCouponStatus(Coupon coupon, CouponStatus status) {
    coupon.updateStatus(status.name());
    if (couponMapper.update(coupon) == 0) {
      throw new OrderCouponUpdateStatusException();
    }
  }
}
