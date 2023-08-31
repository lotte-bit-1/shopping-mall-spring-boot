package com.bit.shoppingmall.app.exception.order;

import com.bit.shoppingmall.app.exception.EntityNotFoundException;

public class OrderEntityNotFoundException extends EntityNotFoundException {

    private static final String message = "주문 정보를 찾을 수 없습니다.";

    public OrderEntityNotFoundException() {
        super(message);
    }
}
