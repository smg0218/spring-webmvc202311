package com.spring.mvc.chap05.entity;

import com.spring.mvc.chap05.repository.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    PasswordEncoder encoder;

    @Test
    @DisplayName("회원가입에 성공해야 한다")
    void saveTest() {
        //given
        Member member = Member.builder()
                .account("kuromi")
                .password(encoder.encode("abc1234!"))
                .name("쿠로미")
                .email("kuromi@gmail.com")
                .build()
                ;

        //when
        boolean flag = memberMapper.save(member);

        //then
        assertTrue(flag);
    }

    @Test
    @DisplayName("lesserafim계정을 조회하면 그 회원의 이름이 라이옹이어야 한다")
    void findMemberTest() {
        //given
        String acc = "lesserafim";

        //when
        Member foundMember = memberMapper.findMember(acc);

        //then
        System.out.println("foundMember = " + foundMember);
        assertEquals("라이옹", foundMember.getName());
    }


    @Test
    @DisplayName("계정명이 newjeans일 경우 중복확인 결과값은 false이어야 한다.")
    void duplicateTest() {
        //given
        String acc = "newjeans";

        //when
        boolean flag = memberMapper.isDuplicate("account", acc);

        //then
        assertFalse(flag);
    }

    @Test
    @DisplayName("계정명이 lesserafim일 경우 중복확인 결과값은 True이어야 한다.")
    void duplicateTrueTest() {
        //given
        String acc = "lesserafim";

        //when
        boolean flag = memberMapper.isDuplicate("account", acc);

        //then
        assertTrue(flag);
    }

    @Test
    @DisplayName("이메일이 abc@naver.com 경우 중복확인 결과값은 true여야 한다.")
    void duplicateEmailTest() {
        //given
        String email = "abc@naver.com";

        //when
        boolean flag = memberMapper.isDuplicate("email", email);

        //then
        assertTrue(flag);
    }

    @Test
    @DisplayName("비밀번호가 암호화되어야 한다")
    void encodingTest() {
        // 인코딩 전 패스워드
        String rawPassword = "abc1234!@";

        // 인코딩 후 패스워드
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("rawPassword = " + rawPassword);
        System.out.println("encodedPassword = " + encodedPassword);
    }

}