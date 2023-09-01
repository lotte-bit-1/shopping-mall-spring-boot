package com.bit.shoppingmall.app.service.order.interfaces;

import com.bit.shoppingmall.app.dto.order.response.ProductOrderDetailDto;
import com.bit.shoppingmall.app.dto.order.response.ProductOrderDto;

import java.util.List;

public interface OrderReadService {
  List<ProductOrderDto> getProductOrdersForMemberCurrentYear(Long memberId) throws Exception;

  ProductOrderDetailDto getOrderDetailsForMemberAndOrderId(Long orderId, Long memberId)
      throws Exception;
}
