package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface CouponMapper {

    int insert(Coupon coupon);

    int update(Coupon coupon);

    Optional<Coupon> selectById(@Param("id") Long id);
}
