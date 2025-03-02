package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.request.LoginRequestDTO;
import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.spring.mvc.chap05.service.LoginResult.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원정보를 전달하면 비밀번호가 암호화되어 디비에 저장된다.")
    void joinTest() {
        //given
        SignUpRequestDTO dto = SignUpRequestDTO.builder()
                .account("kitty")
                .password("kkk1234!")
                .name("고양이")
                .email("kitty123@gmail.com")
                .build();

        String savePath = "D:/spring-prj/upload/2024/01/02";

        //when
        boolean flag = memberService.join(dto, savePath);

        //then
        assertTrue(flag);
    }

    @Test
    @DisplayName("계정명이 kitty인 회원의 로그인시도 결과를 상황별로 검증한다.(성공)")
    void loginTest() {
        //given
        LoginRequestDTO dto = LoginRequestDTO.builder()
                .account("kitty")
                .password("kkk1234!")
                .build();

        //when
        LoginResult result = memberService.authenticate(dto, null, null);

        //then
        assertEquals(SUCCESS, result);
    }

    @Test
    @DisplayName("계정명이 kitty인 회원의 로그인시도 결과를 상황별로 검증한다.(비밀번호 틀림)")
    void loginPasswordFailTest() {
        //given
        LoginRequestDTO dto = LoginRequestDTO.builder()
                .account("kitty")
                .password("kkk1234!@")
                .build();

        //when
        LoginResult result = memberService.authenticate(dto, null, null);

        //then
        assertEquals(NO_PW, result);
    }

    @Test
    @DisplayName("계정명이 kitty인 회원의 로그인시도 결과를 상황별로 검증한다.(계정이 없음)")
    void loginNoAccountTest() {
        //given
        LoginRequestDTO dto = LoginRequestDTO.builder()
                .account("kitty1")
                .password("kkk1234!")
                .build();

        //when
        LoginResult result = memberService.authenticate(dto, null, null);

        //then
        assertEquals(NO_ACC, result);
    }
}