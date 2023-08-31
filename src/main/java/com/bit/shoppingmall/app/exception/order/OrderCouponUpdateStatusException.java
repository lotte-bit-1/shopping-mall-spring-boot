package com.bit.shoppingmall.app.exception.order;

import com.bit.shoppingmall.app.exception.DomainException;

import javax.servlet.http.HttpServletResponse;

/* 주문할 때 쿠폰 업데이트시 에러 */
public class OrderCouponUpdateStatusException extends DomainException {

    private static final String message = "존재하지 않는 쿠폰입니다.";

    public OrderCouponUpdateStatusException() {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
