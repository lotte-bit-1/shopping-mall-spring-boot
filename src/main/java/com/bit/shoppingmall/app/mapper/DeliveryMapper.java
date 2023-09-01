package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.entity.Delivery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DeliveryMapper {

    Optional<Delivery> selectByOrderId(Long orderId);

    List<Delivery> selectall();

    int insert(Delivery delivery);

    int update(Delivery delivery);

    int delete(Long id);
}
