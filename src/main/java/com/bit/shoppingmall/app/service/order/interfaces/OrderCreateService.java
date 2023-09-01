package com.bit.shoppingmall.app.service.order.interfaces;

import com.bit.shoppingmall.app.dto.order.form.OrderCreateForm;
import com.bit.shoppingmall.app.dto.order.request.OrderCreateDto;
import com.bit.shoppingmall.app.entity.Order;

public interface OrderCreateService {
  OrderCreateForm getCreateOrderForm(Long memberId, Long productId) throws Exception;

  Order createOrder(OrderCreateDto orderCreateDto) throws Exception;
}
