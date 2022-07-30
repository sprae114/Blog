package com.blog.service;

import com.blog.domain.Board;
import com.blog.dto.BoardDto;
import com.blog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board editBoard(Long id, BoardDto boardDto){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        board.updateBoard(boardDto);
        return board;
    }
}
