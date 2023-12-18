package com.spring.mvc;

import com.spring.mvc.chap06.entity.Person;
import com.spring.mvc.chap06.mybatis.PersonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
//@ResponseBody
@RestController
@RequestMapping("/rest")
@Slf4j
public class RestApiController {
//    @ResponseBody // 클라이언트에게 JSP를 보내는게 아니라 JSON을 보내는 방법
    @GetMapping("/hello")
    public String hello() {
        log.info("/rest/hello GET!");
        return "hello apple banana";
    }

//    @ResponseBody
    @GetMapping("/food")
    public List<String> food() {
        return List.of("짜장면", "탕수육", "볶음밥");
    }

    @GetMapping("/person")
    public Person person() {
        return new Person("334", "테스트", 50);
    }

    /*
        RestController에서 리턴 타입을 ResponseEntity를 쓰는 이유
        - 클라이언트에게 응답할 때는 메세지 바디 안에 들어있는 데이터도 중요하지만
        - 상태코드와 헤더정보를 포함해야 함
        -> 일반 리턴 타입은 상태코드와 헤더정보를 전송하기 어려움
     */

    @GetMapping("person-list")
    public ResponseEntity<?> personList() {
        Person p1 = new Person("111", "딸긔겅듀", 30);
        Person p2 = new Person("222", "잔망루피", 44);
        Person p3 = new Person("333", "뽀로로로", 55);

        List<Person> personList = List.of(p1, p2, p3);

//        return ResponseEntity
//                .ok(personList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("my-pet", "냥냥이");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(personList)
                ;
    }

    @GetMapping("/bmi")
    public ResponseEntity<?> bmi(
            @RequestParam(required = false) Double cm,
            @RequestParam(required = false) Double kg)
    {
        if(cm == null || kg == null)
            return ResponseEntity.badRequest().body("키랑 몸무게를 필수로 전달하세요!");

        HttpHeaders headers = new HttpHeaders();
        headers.add("fruits", "melon");
        headers.add("pet", "dog");

        double bmi = kg / ((cm / 100) * (cm / 100));

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(bmi)
                ;

    }
}
