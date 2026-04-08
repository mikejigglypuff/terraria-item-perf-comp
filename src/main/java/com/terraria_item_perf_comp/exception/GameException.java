package com.terraria_item_perf_comp.exception;

import org.springframework.http.HttpStatus;

/**
 * API 흐름/요청 검증 실패 등을 상태코드와 함께 표현하는 런타임 예외입니다.
 */
public class GameException extends RuntimeException {

    private final HttpStatus status;

    public GameException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

