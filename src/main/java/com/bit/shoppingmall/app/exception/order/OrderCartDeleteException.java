package com.bit.shoppingmall.app.exception.order;

import com.bit.shoppingmall.app.exception.DomainException;

import javax.servlet.http.HttpServletResponse;

/* 장바구니 주문할 때 장바구니 삭제 시 에러 */
public class OrderCartDeleteException extends DomainException {

    private static final String message = "존재하지 않는 장바구니 입니다.";

    public OrderCartDeleteException() {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
