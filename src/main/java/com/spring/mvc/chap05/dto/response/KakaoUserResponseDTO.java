package com.spring.mvc.chap05.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserResponseDTO {
    private long id;

    @JsonProperty("connected_at") //JSON과 Java의 변수명이 틀리므로 알려준다.
    private LocalDateTime connectedAt;

    private Properties properties;

    @Setter @Getter @ToString
    public static class Properties {
        private String nickname;
        @JsonProperty("profile_image")
        private String profileImage;
        @JsonProperty("thumbnail_image")
        private String thumbnailImage;
    }
}
