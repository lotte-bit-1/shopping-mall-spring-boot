package com.bit.shoppingmall.app.service.order.manager;

import com.bit.shoppingmall.app.entity.Delivery;
import com.bit.shoppingmall.app.enums.DeliveryStatus;
import com.bit.shoppingmall.app.exception.delivery.DeliveryEntityNotFoundException;
import com.bit.shoppingmall.app.exception.order.OrderDeliveryAlreadyCanceledException;
import com.bit.shoppingmall.app.exception.order.OrderDeliveryProcessingException;
import com.bit.shoppingmall.app.exception.order.OrderDeliveryUpdateStatusException;
import com.bit.shoppingmall.app.mapper.DeliveryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDeliveryManager {

  private final DeliveryMapper deliveryMapper;

  public Delivery determineDelivery(Long orderId) {
    return deliveryMapper
        .selectByOrderId(orderId)
        .orElseThrow(DeliveryEntityNotFoundException::new);
  }

  public void checkDeliveryCanceled(Delivery delivery) {
    if (delivery.getStatus().equals(DeliveryStatus.CANCELED.name())) {
      throw new OrderDeliveryAlreadyCanceledException();
    }
  }

  public void checkDeliveryProcessing(Delivery delivery) {
    if (delivery.getStatus().equals(DeliveryStatus.PROCESSING.name())) {
      throw new OrderDeliveryProcessingException();
    }
  }

  public void updateDeliveryStatus(Delivery delivery, DeliveryStatus status) {
    delivery.updateStatus(status.name());
    if (deliveryMapper.update(delivery) == 0) {
      throw new OrderDeliveryUpdateStatusException();
    }
  }

  public int createDelivery(Delivery delivery) {
    return deliveryMapper.insert(delivery);
  }
}
