package com.finance.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //프로젝트 전체에서 발생하는 에러를 여기서 낚아챔
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        //에러가 나면 프론트엔드에서 이해하기 쉽게 JSON에 담기.
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", "FAIL");
        errorResponse.put("errorMessage", e.getMessage()); //작액 부족 메세지

        //400 Bad Request == 사용자 잘못이라는 뜻
        return ResponseEntity.badRequest().body(errorResponse);
    }
}