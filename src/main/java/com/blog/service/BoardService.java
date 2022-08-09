package com.blog.service;

import com.blog.domain.Board;
import com.blog.dto.board.BoardSaveRequestDto;
import com.blog.dto.board.BoardUpdateRequestDto;
import com.blog.dto.board.BoardResponseDto;
import com.blog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardSaveRequestDto requestDto, String saveLoginId){
        Board board = requestDto.toEntity();
        board.setUserId(saveLoginId);

        return boardRepository.save(board).getId();
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
                new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto findByTitle(String title){
        Board board = boardRepository.findByTitle(title).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. title ="+ title));
        return new BoardResponseDto(board);
    }


    @Transactional
    public void delete (Long id){
        Board board = boardRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));
        boardRepository.delete(board);
    }

}
