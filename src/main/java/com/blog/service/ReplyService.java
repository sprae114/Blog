package com.blog.service;

import com.blog.domain.Board;
import com.blog.domain.Reply;
import com.blog.dto.board.BoardResponseDto;
import com.blog.dto.board.BoardSaveRequestDto;
import com.blog.dto.reply.ReplySaveRequestDto;
import com.blog.repository.ReplyRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Transactional
    public Long saveReply(ReplySaveRequestDto requestDto){return replyRepository.save(requestDto.toEntity()).getId();
    }


    @Transactional
    public void deleteReply(Long deleteId){
        Reply reply = replyRepository.findById(deleteId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 없습니다."));
        replyRepository.delete(reply);
    }

    @Transactional
    public void deleteBoard(Long boardId){
        List<Reply> allByBoardId = replyRepository.findAllByBoardId(boardId);
        for (Reply reply : allByBoardId){
            replyRepository.delete(reply);
        }
    }
}
