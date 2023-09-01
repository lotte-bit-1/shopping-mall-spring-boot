package com.bit.shoppingmall.app.service.order.manager;

import com.bit.shoppingmall.app.dto.order.response.ProductOrderDetailDto;
import com.bit.shoppingmall.app.dto.order.response.ProductOrderDto;
import com.bit.shoppingmall.app.entity.Order;
import com.bit.shoppingmall.app.enums.OrderStatus;
import com.bit.shoppingmall.app.exception.order.OrderAlreadyCanceledException;
import com.bit.shoppingmall.app.exception.order.OrderEntityNotFoundException;
import com.bit.shoppingmall.app.exception.order.OrderUpdateStatusException;
import com.bit.shoppingmall.app.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderManager {

  private final OrderMapper orderMapper;

  public Order determineOrder(Long orderId) {
    return orderMapper.selectById(orderId).orElseThrow(OrderEntityNotFoundException::new);
  }

  public int createOrder(Order order) {
    return orderMapper.insert(order);
  }

  public void checkAlreadyOrdered(Order order) {
    if (order.getStatus().equals(OrderStatus.CANCELED.name())) {
      throw new OrderAlreadyCanceledException();
    }
  }

  public void updateOrderStatus(Order order, OrderStatus status) {
    order.updateStatus(status.name());
    if (orderMapper.update(order) == 0) {
      throw new OrderUpdateStatusException();
    }
  }

  public List<ProductOrderDto> getProductOrdersForMemberCurrentYear(
      Long memberId) {
    return orderMapper.selectProductOrdersForMemberCurrentYear(memberId);
  }

  public ProductOrderDetailDto getOrderDetailsForMemberAndOrderId(
      Long orderId, Long memberId) {
    final Map<String, Long> orderIdAndMemberIdParameterMap = new HashMap<>();
    orderIdAndMemberIdParameterMap.put("orderId", orderId);
    orderIdAndMemberIdParameterMap.put("memberId", memberId);
    return orderMapper
        .selectOrderDetailsForMemberAndOrderId(orderIdAndMemberIdParameterMap)
        .orElseThrow(OrderEntityNotFoundException::new);
  }
}
