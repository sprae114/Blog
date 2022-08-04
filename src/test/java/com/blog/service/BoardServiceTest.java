package com.blog.service;

import com.blog.domain.Board;
import com.blog.dto.board.BoardResponseDto;
import com.blog.dto.board.BoardSaveRequestDto;
import com.blog.dto.board.BoardUpdateRequestDto;
import com.blog.repository.BoardRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardService boardService;

    @BeforeEach
    void beforeEach(){
        BoardSaveRequestDto boardSaveDto = new BoardSaveRequestDto("테스트 제목 1", "테스트 내용 1");
        boardService.save(boardSaveDto);
    }

    @AfterEach
    void afterEach(){boardRepository.deleteAll();}

    @DisplayName("글 저장 - 성공")
    @Test
    void saveBolg() {
        BoardSaveRequestDto boardSaveDto = new BoardSaveRequestDto("테스트 제목 2", "테스트 내용 2");
        Long saveId = boardService.save(boardSaveDto);

        //then
        Board saveBoard = boardRepository.findById(saveId).get();
        assertThat(boardSaveDto.getTitle()).isEqualTo(saveBoard.getTitle());
        assertThat(boardSaveDto.getContent()).isEqualTo(saveBoard.getContent());
    }

    @DisplayName("글 저장 - 실패 : 제목 글자수 미만")
    @Test
    void saveBolgTitle() {
        BoardSaveRequestDto boardSaveDto = new BoardSaveRequestDto("A", "A");
        Long saveId = boardService.save(boardSaveDto);

        //then
        Board saveBoard = boardRepository.findById(saveId).get();
        assertThat(boardSaveDto.getTitle()).isEqualTo(saveBoard.getTitle());
        assertThat(boardSaveDto.getContent()).isEqualTo(saveBoard.getContent());
    }

    @DisplayName("Board 전체 찾기")
    @Test
    void searchBlog(){
        //given
        BoardSaveRequestDto boardSaveDto1 = new BoardSaveRequestDto("테스트 제목 2", "테스트 내용 2");
        BoardSaveRequestDto boardSaveDto2 = new BoardSaveRequestDto("테스트 제목 3", "테스트 내용 3");
        Long saveId1 = boardService.save(boardSaveDto1);
        Long saveId2 = boardService.save(boardSaveDto2);

        //when
        List<Board> boards = boardRepository.findAll();

        //then
        assertThat(boards.size()).isEqualTo(3);
    }

    @DisplayName("Board 찾기 - 존재하지 O")
    @Test
    void searchBlogPresent(){
        //given
        BoardSaveRequestDto boardSaveDto = new BoardSaveRequestDto("테스트 제목 2", "테스트 내용 2");
        Long saveId = boardService.save(boardSaveDto);

        try {
            BoardResponseDto findBoardDto = boardService.findById(saveId);
            assertThat(findBoardDto.getTitle()).isEqualTo(boardSaveDto.getTitle());
            assertThat(findBoardDto.getContent()).isEqualTo(boardSaveDto.getContent());
        }

        catch (IllegalArgumentException e){
            Assertions.assertEquals("헤당 게시글이 없습니다. id=" + saveId, e.getMessage());
        }

    }

    @DisplayName("Board 찾기 - 존재하지 X")
    @Test
    void searchBlogEmpty(){
        //given
        BoardSaveRequestDto boardSaveDto = new BoardSaveRequestDto("테스트 제목 2", "테스트 내용 2");
        Long saveId = boardService.save(boardSaveDto);
        Long noData = saveId + 3;

        try {
            BoardResponseDto findBoardDto = boardService.findById(saveId);
            assertThat(findBoardDto.getTitle()).isEqualTo(boardSaveDto.getTitle());
            assertThat(findBoardDto.getContent()).isEqualTo(boardSaveDto.getContent());
        }

        catch (IllegalArgumentException e){
            Assertions.assertEquals("헤당 게시글이 없습니다. id=" + noData, e.getMessage());
        }

    }


    @DisplayName("블로그 수정 - 성공")
    @Test
    void editBlog() {
        //given
        BoardSaveRequestDto boardSaveDto = new BoardSaveRequestDto("테스트 제목 2", "테스트 내용 2");
        Long saveId = boardService.save(boardSaveDto);
        BoardUpdateRequestDto boardUpdateDto = new BoardUpdateRequestDto("수정 제목 2", "수정 내용2");

        //when
        boardService.update(saveId, boardUpdateDto);

        //then
        Board saveBoard =  boardRepository.findById(saveId).get();
        assertThat(boardUpdateDto.getTitle()).isEqualTo(saveBoard.getTitle());
        assertThat(boardUpdateDto.getContent()).isEqualTo(saveBoard.getContent());
    }


    @DisplayName("블로그 수정 - 이름 없는 경우")
    @Test
    void editBlogNoName() {
        //given
        BoardSaveRequestDto boardSaveDto = new BoardSaveRequestDto("테스트 제목 2", "테스트 내용 2");
        Long saveId = boardService.save(boardSaveDto);
        BoardUpdateRequestDto boardUpdateDto = new BoardUpdateRequestDto("수정 제목 2", "수정 내용2");

        //when, then
        Long noData = saveId + 3;
        assertThatThrownBy(() -> boardService.update(noData, boardUpdateDto))
                .isInstanceOf(IllegalArgumentException.class);

    }
}