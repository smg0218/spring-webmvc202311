package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.common.Page;
import com.spring.mvc.chap05.dto.ReplyDetailResponseDTO;
import com.spring.mvc.chap05.entity.Reply;
import com.spring.mvc.chap05.repository.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {

    private final ReplyMapper replyMapper;


    // 댓글 목록 조회
    public List<ReplyDetailResponseDTO> getList(long boardNo, Page page) {

        List<Reply> replyList = replyMapper.findAll(boardNo, page);

        return replyList.stream()
                .map(ReplyDetailResponseDTO::new)
                .collect(Collectors.toList());
    }

}
