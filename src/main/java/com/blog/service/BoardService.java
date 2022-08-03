package com.blog.service;

import com.blog.domain.Board;
import com.blog.dto.BoardDto;
import com.blog.dto.BoardSaveRequestDto;
import com.blog.dto.BoardUpdateRequestDto;
import com.blog.dto.BoardResponseDto;
import com.blog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardSaveRequestDto requestDto){
        return boardRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, BoardUpdateRequestDto requestDto){
        Board saveBoard = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));
        saveBoard.updateBoard(requestDto);

        return id;
    }

    @Transactional
    public BoardResponseDto findById(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("헤당 게시글이 없습니다. id="+id));
        return new BoardResponseDto(board);
    }


    @Transactional
    public void delete (Long id){
        Board board = boardRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));
        boardRepository.delete(board);
    }


}
