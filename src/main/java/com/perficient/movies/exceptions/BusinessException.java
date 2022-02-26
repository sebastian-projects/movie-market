package com.perficient.movies.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCodesEnum code;

    public BusinessException(String message, ErrorCodesEnum code) {
        super(message);
        this.code = code;
    }
}
