package com.spring.mvc.interceptor;

import com.spring.mvc.chap05.entity.Member;
import com.spring.mvc.chap05.repository.MemberMapper;
import com.spring.mvc.chap05.service.MemberService;
import com.spring.mvc.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

import static com.spring.mvc.util.LoginUtils.AUTO_LOGIN_COOKIE;

@Configuration
@RequiredArgsConstructor
public class AutoLoginInterceptor implements HandlerInterceptor {
    private final MemberMapper memberMapper;
    private final MemberService memberService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 사이트에 들어오면 자동로그인 쿠키를 가진 클라이언트인지 체크
        // 단순히 request로 쿠키를 가져오면 쿠키 배열을 가져오므로
        // Spring에서 지원하는 기본 메서드로 Cookie값을 자동으로 확인해서 돌려준다.
        Cookie autoLoginCookie = WebUtils.getCookie(request, AUTO_LOGIN_COOKIE);

        // 2. 자동로그인쿠키가 있다면 로그인 처리를 수행
        if (autoLoginCookie != null) {
            // 3. 지금 읽은 쿠키에 들어있는 session_id를 확인
            String sessionId = autoLoginCookie.getValue();

            // 4. DB에서 쿠키에 들어있는 세션아이디를 가진 회원을 조회
            Member member = memberMapper.findMemberByCookie(sessionId);

            // 5. 회원이 정상 조회되고 자동로그인 만료시간 이전이면 로그인을 실행해준다.
            if (member != null && LocalDateTime.now().isBefore(member.getLimitTime())) {
                memberService.maintainLoginState(request.getSession(), member.getAccount());
            } else if (LocalDateTime.now().isAfter(member.getLimitTime())) {

            }
        }

        return true;
    }
}
