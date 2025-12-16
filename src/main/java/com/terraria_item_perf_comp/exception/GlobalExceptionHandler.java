package com.terraria_item_perf_comp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // 원인(Cause) 로깅
        if (e.getCause() != null) {
            logger.error("Exception occurred - Cause: {}", e.getCause().getMessage(), e.getCause());
        }
        
        // 전체 Exception과 StackTrace 로깅
        logger.error("Exception occurred - Message: {}", e.getMessage(), e);
        
        // 클라이언트에게는 간단한 에러 메시지만 반환
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage());
    }
}

