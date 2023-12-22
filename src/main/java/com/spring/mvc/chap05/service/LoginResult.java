package com.spring.mvc.chap05.service;

public enum LoginResult {
    SUCCESS, // 로그인 성공
    NO_ACC, // 회원이 없을 때
    NO_PW // 비밀번호가 일치하지 않을 때
}
