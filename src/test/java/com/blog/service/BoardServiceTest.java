package com.blog.service;

import com.blog.domain.Board;
import com.blog.dto.BoardDto;
import com.blog.repository.BoardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardService boardService;

    @AfterEach
    void afterEach(){boardRepository.deleteAll();}


    @Test
    void 블로그저장하기() {
        //given
        Board board1 = new Board("userA","제목1", "내용 1");

        //when
        boardRepository.save(board1);

        //then
        Board saveBoard = boardRepository.findById(board1.getId()).get();
        assertThat(board1).isEqualTo(saveBoard);
    }


    @Test
    void 블로그전체Board찾기(){
        //given
        Board board1 = new Board("userA", "제목1", "내용 1");
        Board board2 = new Board("userB","제목2", "내용 2");
        boardRepository.save(board1);
        boardRepository.save(board2);

        //when
        List<Board> boards = boardRepository.findAll();

        //then
        assertThat(boards.size()).isEqualTo(2);
        assertThat(boards).contains(board1,board2);
    }

    @Test
    void 블로그Board찾기오류(){
        //given
        Board board1 = new Board("userA", "제목1", "내용 1");
        boardRepository.save(board1);

        try {
            boardRepository.findById(3L).get();
        }
        catch (NoSuchElementException e){
            Assertions.assertEquals("No value present", e.getMessage());
        }

    }


    @Test
    void 블로그수정하기() {
        //given
        Board board1 = new Board("userA", "제목1", "내용 1");
        BoardDto updateBoardDto = new BoardDto("제목2", "내용 2");
        boardRepository.save(board1);

        //when
        boardService.editBoard(board1.getId(), updateBoardDto);

        //then
        Board saveBoard =  boardRepository.findById(board1.getId()).get();
        assertThat(updateBoardDto.getTitle()).isEqualTo(saveBoard.getTitle());
        assertThat(updateBoardDto.getContent()).isEqualTo(saveBoard.getContent());
    }


    @Test
    void 블로그수정하기_이름_없는_경우() {
        //given
        Board board1 = new Board("userA", "제목1", "내용 1");
        BoardDto updateBoardDto = new BoardDto("제목2", "내용 2");
        boardRepository.save(board1);

        assertThatThrownBy(() -> boardService.editBoard(3L, updateBoardDto))
                .isInstanceOf(IllegalArgumentException.class);

    }
}