package com.bit.shoppingmall.app.service.order.interfaces;

import com.bit.shoppingmall.app.dto.order.form.OrderCartCreateForm;
import com.bit.shoppingmall.app.dto.order.request.OrderCartCreateDto;
import com.bit.shoppingmall.app.entity.Order;

public interface OrderCartCreateService {

  OrderCartCreateForm getCreateCartOrderForm(Long memberId) throws Exception;

  Order createCartOrder(OrderCartCreateDto orderCartCreateDto) throws Exception;
}
