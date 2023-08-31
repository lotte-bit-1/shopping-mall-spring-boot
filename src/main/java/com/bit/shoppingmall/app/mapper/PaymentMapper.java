package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface PaymentMapper {

    int insert(Payment payment);

    Optional<Payment> selectByOrderId(@Param("orderId") Long orderId);
}
