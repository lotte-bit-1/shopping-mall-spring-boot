package com.bit.shoppingmall.app.service.order.manager;

import com.bit.shoppingmall.app.entity.Payment;
import com.bit.shoppingmall.app.exception.payment.PaymentEntityNotFoundException;
import com.bit.shoppingmall.app.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPaymentManager {

  private final PaymentMapper paymentMapper;

  public Payment determinePaymentByOrderId(Long orderId) {
    return paymentMapper
        .selectByOrderId(orderId)
        .orElseThrow(PaymentEntityNotFoundException::new);
  }

  public int createPayment(Payment payment) throws Exception {
    return paymentMapper.insert(payment);
  }
}
