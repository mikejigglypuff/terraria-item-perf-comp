package com.terraria_item_perf_comp.exception;

import org.springframework.http.HttpStatus;

public class BalanceGameException extends GameException {

    public BalanceGameException(HttpStatus status, String message) {
        super(status, message);
    }
}
