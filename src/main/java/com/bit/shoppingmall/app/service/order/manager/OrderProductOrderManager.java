package com.bit.shoppingmall.app.service.order.manager;

import com.bit.shoppingmall.app.entity.ProductOrder;
import com.bit.shoppingmall.app.mapper.ProductOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductOrderManager {

  private final ProductOrderMapper productOrderMapper;

  public List<ProductOrder> determineProductOrdersByOrderId(Long orderId) {
    return productOrderMapper.selectAllByOrderId(orderId);
  }

  public int createProductOrder(ProductOrder productOrder) {
    return productOrderMapper.insert(productOrder);
  }

  public int createProductOrders(List<ProductOrder> productOrders)
      throws Exception {
    return productOrderMapper.bulkInsert(productOrders);
  }
}
