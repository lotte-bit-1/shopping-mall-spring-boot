package com.bit.shoppingmall.app.exception.member;

import com.bit.shoppingmall.app.exception.EntityNotFoundException;

public class MemberEntityNotFoundException extends EntityNotFoundException {

    private static final String message = "회원 정보를 찾을 수 없습니다.";

    public MemberEntityNotFoundException() {
        super(message);
    }
}
