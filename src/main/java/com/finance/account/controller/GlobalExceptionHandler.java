package com.finance.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//프로젝트 전역에서 발생하는 모든 에러를 한곳으로 Catch하는 중앙 통제실 기능.
//예상치 못한 버그나 로직 에러가 발생했을 때 게임이 튕기지 않게 막는 Global Error Manager 역할

@RestControllerAdvice //프로젝트 전체에서 발생하는 에러를 여기서 낚아챔
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {

        //에러 내용을 담을 상자를 준비(Map/JSON)
        Map<String, String> errorResponse = new HashMap<>();

        //정의한 에러 코드와 실제 발생한 에러 메시지를 담기.
        errorResponse.put("errorCode", "FAIL"); //프론트엔드와 약속한 고유 에러 코드
        errorResponse.put("errorMessage", e.getMessage()); //잔액이 부족합니다.
        return ResponseEntity.badRequest().body(errorResponse);
    }
}