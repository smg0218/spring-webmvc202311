//package com.spring.mvc.chap05.controller;
//
//import com.spring.mvc.chap06.entity.Person;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
////@Controller
////@ResponseBody
//@RestController
//@RequestMapping("/rests")
//@Slf4j
//public class RestApiController {
//    @GetMapping("/hello")
////    @ResponseBody  // 클라이언트에게 JSP를 보내는게 아니라 JSON을 보내는 방법
//    public String hello() {
//        log.info("/rest/hello GET!");
//        return "hello apple banana!!";
//    }
//    @GetMapping("/food")
////    @ResponseBody
//    public List<String> food() {
//        return List.of("짜장면", "볶음밥", "탕수육");
//    }
//    @GetMapping("/person")
//    public Person person() {
//        return new Person("334", "말포이", 50);
//    }
//
//
//    /*
//         RestController에서 리턴타입을 ResponseEntity를 쓰는 이유
//         - 클라이언트에게 응답할 때는 메시지 바디안에 들어 있는 데이터도 중요하지만
//         - 상태코드와 헤더정보를 포함해야 한다
//         - 근데 일반 리턴타입은 상태코드와 헤더정보를 전송하기 어렵다
//     */
//
//    @GetMapping("/person-list")
//    public ResponseEntity<?> personList() {
//        Person p1 = new Person("111", "딸긔겅듀", 30);
//        Person p2 = new Person("222", "잔망루피", 44);
//        Person p3 = new Person("333", "뽀로로로", 55);
//
//        List<Person> personList = List.of(p1, p2, p3);
//
////        HttpHeaders headers = new HttpHeaders();
////        headers.add("my-pet", "냥냥이");
//
//
//        return ResponseEntity
//                .ok()
////                .headers(headers)
//                .body(personList);
//    }
//
//    @GetMapping("/bmi")
//    public ResponseEntity<?> bmi(
//            @RequestParam(required = false) Double cm,
//            @RequestParam(required = false) Double kg) {
//
//
//        if (cm == null || kg == null) {
//            return ResponseEntity.badRequest().body("키랑 몸무게를 필수로 전달하세요!!");
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("fruits", "melon");
//        headers.add("pet", "dog");
//
//        double bmi = kg / ((cm / 100) * (cm / 100));
//
//        return ResponseEntity.ok().headers(headers).body(bmi);
//    }
//
//
//}