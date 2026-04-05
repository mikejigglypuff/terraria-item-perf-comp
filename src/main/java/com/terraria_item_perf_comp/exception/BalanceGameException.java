package com.terraria_item_perf_comp.exception;

import org.springframework.http.HttpStatus;

public class BalanceGameException extends RuntimeException {

    private final HttpStatus status;

    public BalanceGameException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
