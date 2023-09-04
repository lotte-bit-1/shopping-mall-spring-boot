package com.bit.shoppingmall.app.exception.response;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorResponse {

    private final String code;

    private final String message;

    private final Map<String, String> validation;

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = (validation != null ? validation : new HashMap<>());
    }

    public void addValidation(String field, String errorMessage) {
        this.validation.put(field, errorMessage);
    }
}
