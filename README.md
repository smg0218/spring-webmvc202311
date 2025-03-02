# 스프링 web mvc 학습하기

## gabia : 도메인 사이트
https://www.gabia.com

## application.properties tomcat 포트변경
server.port=8181

## application.properties 기본 경로와 확장자 설정
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

## jsp mvc패턴 관계
손님 (클라이언트)

직원 3명
1번째 직원 (홀 메뉴만 주는 서빙직원 - 컨트롤러)
2번째 직원 (플레이팅 - 서비스)
3번째 직원 (재료를 갖다주고 가져오는 주방 보조 - 레퍼지토리)

주방 (서버)

## REST API 설명
- **`GET /users`**: 모든 사용자를 조회합니다.
- **`GET /users/1`**: 식별자가 1인 사용자를 조회합니다.
- **`POST /users`**: 새로운 사용자를 생성합니다.
- **`PUT /users/1`**: 식별자가 1인 사용자의 정보를 수정합니다.
- **`PATCH /users/1`**: 식별자가 1인 사용자의 일부 정보를 수정합니다.
- **`DELETE /users/1`**: 식별자가 1인 사용자를 삭제합니다.
- **`GET /users/1/posts`**: 식별자가 1인 사용자의 모든 게시물을 조회합니다.
- **`GET /users/1/posts/2`**: 식별자가 1인 사용자가 작성한 식별자가 2인 게시물을 조회합니다.
- **`POST /users/1/posts`**: 식별자가 1인 사용자가 새로운 게시물을 생성합니다.

## Postman API
Rest API 테스트 도구로 json파일을 편하게 볼 수 있도록 해줌


## 동기통신 (SOAP) [@Controller](동기 + 비동기도 @Controller, 단 비동기가 필요한 부분에 @ResponseBody 붙이기)
하나의 요청이 응답될 때 까지 다른 요청들은 대기상태

## 비동기 통신 (AJAX + REST API) -> AJAX 프레임워크 : jQuery, Angular, React... axios [@RestController]
하나의 요청이 응답되기 전에 다른 요청이 들어오면
나중에 요청된 응답이 먼저 응답될수도 있음

## 스프링 시큐리티 모듈에서 자동으로 생성된 로그인페이지 끄는 방법
config 폴더에 SecurityConfig파일 만들기

## 흐름에 따른 제작장법
DB생성 <br>

entity 생성 <br>

mapper 생성 <br>

service 제작 <br>