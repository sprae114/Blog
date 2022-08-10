package com.blog.service;

import com.blog.domain.Reply;
import com.blog.dto.board.BoardSaveRequestDto;
import com.blog.dto.reply.ReplySaveRequestDto;
import com.blog.repository.BoardRepository;
import com.blog.repository.ReplyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ReplyServiceTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ReplyService replyService;


    @AfterEach
    void afterEach(){boardRepository.deleteAll();}

    @DisplayName("댓글 삭제 - 정상")
    @Test
    void deleteReplyCorrect() {
        //given
        BoardSaveRequestDto boardSaveDto = new BoardSaveRequestDto("테스트 제목 1", "테스트 내용 1");
        Long saveBoardId = boardService.save(boardSaveDto, "UserA");

        Long saveReply1 = replyService.saveReply(new ReplySaveRequestDto("테스트 내용1", "UserA", saveBoardId));
        Long saveReply2 = replyService.saveReply(new ReplySaveRequestDto("테스트 내용2", "UserB", saveBoardId));

        //when
        replyService.deleteReply(saveReply1);

        //then
        assertThat(replyRepository.findById(saveReply1)).isEmpty();
        assertThat(boardRepository.findById(saveBoardId)).isPresent();
    }

    @DisplayName("댓글 삭제 - 실패 : 해당 댓글 없을때")
    @Test
    void deleteReplyFail() {
        //given
        BoardSaveRequestDto boardSaveDto = new BoardSaveRequestDto("테스트 제목 1", "테스트 내용 1");
        Long saveBoardId = boardService.save(boardSaveDto, "UserA");

        Long saveReply1 = replyService.saveReply(new ReplySaveRequestDto("테스트 내용1", "UserA", saveBoardId));
        Long saveReply2 = replyService.saveReply(new ReplySaveRequestDto("테스트 내용2", "UserB", saveBoardId));

        //when

        try {
            replyService.deleteReply(saveReply1 + 3);

        }catch (IllegalArgumentException e){
            org.junit.jupiter.api.Assertions.assertEquals("해당 댓글이 없습니다." , e.getMessage());
        }

    }

}