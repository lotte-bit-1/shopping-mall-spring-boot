package com.bit.shoppingmall.app.mapper;

import com.bit.shoppingmall.app.dto.order.response.ProductOrderDetailDto;
import com.bit.shoppingmall.app.dto.order.response.ProductOrderDto;
import com.bit.shoppingmall.app.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface OrderMapper {
    int insert(Order order);

    int update(Order order);

    int delete(@Param("id") Long id);

    Optional<Order> selectById(@Param("id") Long id);

    List<ProductOrderDto> selectProductOrdersForMemberCurrentYear(Long memberId);

    Optional<ProductOrderDetailDto> selectOrderDetailsForMemberAndOrderId(Map<String, Long> orderIdAndMemberIdParameterMap);
}
