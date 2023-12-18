package com.spring.mvc.chap05.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.mvc.chap05.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
// JSON데이터의 이름을 바꿔주는 것
public class ReplyDetailResponseDTO {
    private long rno;
    private String text;
    private String writer;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:MM")
    private LocalDateTime regDate;
    
    // 엔티티를 DTO로 바꿔주는 메서드(생성자)
    public ReplyDetailResponseDTO(Reply reply) {
        this.rno = reply.getReplyNo();
        this.text = reply.getReplyText();
        this.writer = reply.getReplyWriter();
        this.regDate = reply.getReplyDate();
    }
}
