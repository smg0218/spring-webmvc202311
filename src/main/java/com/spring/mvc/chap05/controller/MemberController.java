package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap05.dto.request.LoginRequestDTO;
import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import com.spring.mvc.chap05.service.LoginResult;
import com.spring.mvc.chap05.service.MemberService;
import com.spring.mvc.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.spring.mvc.util.LoginUtils.isAutoLogin;
import static com.spring.mvc.util.LoginUtils.isLogin;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 양식 요청
    @GetMapping("/sign-up")
    public String signUp() {
        log.info("/members/sign-up GET : forwarding to sign-up.jsp");
        return "members/sign-up";
    }

    // 아이디, 이메일 중복체크 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<?> check(String type, String keyword) {
        log.info("/members/check?type={}&keyword={} ASYNC GET", type, keyword);
        boolean flag = memberService.checkDuplicateValue(type, keyword);
        log.debug("중복체크 결과 : {}", flag);

        return ResponseEntity.ok().body(flag);
    }

    //회원가입 처리
    @PostMapping("/sign-up")
    public String signUp(SignUpRequestDTO dto) {
        log.info("/member/sign-up POST !");
        log.debug("parameter: {}", dto);

        boolean flag = memberService.join(dto);

        return flag ? "redirect:/board/list" : "redirect:/members/sign-up";
    }

    @GetMapping("/sign-in")
    public String signIn(HttpSession session) {
//        if (session.getAttribute("login")!= null) {
//            return "redirect:/";
//        }

        log.info("/members/sign-in GET - forwarding to sign-in.jsp");

        return "members/sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(LoginRequestDTO dto
// Model에 담긴 데이터는 리다이렉트시 jsp로 가지 않는다.
// 왜나면 리다이렉트는 요청이 2번 들어가서 첫번째 요청시 보관한 데이터가 소질된다.
    , RedirectAttributes ra
    , HttpServletResponse response
    , HttpServletRequest request) {
        log.info("/members/sign-in POST!");
        log.debug("parameer: {}", dto);

        LoginResult result = memberService.authenticate(dto, request.getSession(), response);
        log.debug("login result: {}", result);

        ra.addFlashAttribute("msg", result);

        if (result == LoginResult.SUCCESS) { // 로그인 성공시
            // makeLoginCookie(dto, response);  // 쿠키로 로그인 유지

            // 세션으로 로그인 유지
            memberService.maintainLoginState(request.getSession(), dto.getAccount());

            return "redirect:/";
        }
        return "redirect:/members/sign-in"; // 로그인 실패시
    }

    // 로그아웃 요청 처리
    @GetMapping("/sign-out")
    public String signOut(
             HttpServletRequest request
             , HttpServletResponse response
//            HttpSession session
    ) {
        // 세션 얻기
        HttpSession session = request.getSession();

        // 로그인 상태인지 확인
        if(isLogin(session)) {
            // 자동 로그인 상태인지도 확인
            if(isAutoLogin(request)) {
                // 쿠키를 삭제해주고 DB데이터도 원래대로 돌려놓는다.
                memberService.autoLoginClear(request, response);
            }

            // 세션에서 로그인 정보 기록 삭제
            session.removeAttribute("login");

            // 세션을 초기화(RESET)
            session.invalidate();

            return "redirect:/";
        }

        return "redirect:/members/sign-in";
    }

    private static void makeLoginCookie(LoginRequestDTO dto, HttpServletResponse response) {
        // 쿠키에 로그인 기록을 저장
        Cookie cookie = new Cookie(LoginUtils.LOGIN_KEY, dto.getAccount());

        // 쿠키 정보 세팅
        cookie.setPath("/"); // 이 쿠키는 모든경로에서 들고다녀야 함
        cookie.setMaxAge(60); // 쿠키 수명 설정

        // 쿠키를 클라이언트에게 전송 (Response객체 필요)
        response.addCookie(cookie);
    }

}
