package com.bit.shoppingmall.app.exception.likes;

import com.bit.shoppingmall.app.exception.EntityNotFoundException;

public class LikesEntityNotFoundException extends EntityNotFoundException {

    private static final String message = "회원의 찜목록이 비어있습니다.";

    public LikesEntityNotFoundException() {
        super(LikesEntityNotFoundException.message);
    }
}
