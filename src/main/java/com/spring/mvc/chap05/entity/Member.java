package com.spring.mvc.chap05.entity;

import lombok.*;

import java.time.LocalDateTime;

/*
-- 회원 관리 테이블
CREATE TABLE tbl_member (
    account VARCHAR(50),
    password VARCHAR(150) NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    auth VARCHAR(20) DEFAULT 'COMMON',
    reg_date DATETIME DEFAULT current_timestamp,
    CONSTRAINT pk_member PRIMARY KEY (account)
);
 */
@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    private String account;
    private String password;
    private String name;
    private String email;
    private Auth auth;
    private LocalDateTime regDate;
    private String sessionId;
    private LocalDateTime limitTime;
    @Setter
    private String profileImage; // 프로필 사진 이미지 경로
    private LoginMethod loginMethod; // 로그인 방법 (kakao, naver, google...)

    public enum LoginMethod {
        COMMON, KAKAO, GOOGLE, NAVER
    }
}
