package com.blog.controller;

import com.blog.dto.board.BoardResponseDto;
import com.blog.dto.board.BoardSaveRequestDto;
import com.blog.repository.BoardRepository;
import com.blog.service.BoardService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    MockMvc mockMvc;  //@AutoConfigureMockMvc를 추가해야함.

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    void beforeEach() {
        BoardSaveRequestDto boardSaveDto = new BoardSaveRequestDto("테스트 제목 1", "테스트 내용 1");
        boardService.save(boardSaveDto, "UserA");
    }

    @AfterEach
    void afterEach(){boardRepository.deleteAll();}

    @DisplayName("글 등록 테스트- 입력값 정상")
    @Test
    void testAddFormCorrect() throws Exception{
        mockMvc.perform(post("/boards/add")
                        .param("title", "테스트 제목")
                        .param("content", "테스트 내용1234567890")) //괄호위치
                .andExpect(status().is3xxRedirection()) //redirect 때문에
                .andExpect(view().name("redirect:/"));

        assertTrue(boardRepository.existsByTitle("테스트 제목"));
        assertTrue(boardRepository.existsByContent("테스트 내용1234567890"));
    }


    @DisplayName("글 등록 테스트- 제목 검증 실패")
    @Test
    void testAddFormTitleFail() throws Exception{
        mockMvc.perform(post("/boards/add")
                        .param("title", "")
                        .param("content", "테스트 내용1234567890"))
                .andExpect(status().isOk()) //redirect 때문에
                .andExpect(view().name("boards/addForm"));
    }

    @DisplayName("글 수정 테스트 - 성공")
    @Test
    void editCorrect() throws Exception{
        BoardResponseDto findBoard = boardService.findByTitle("테스트 제목 1");

        mockMvc.perform(post("/boards/{id}/edit", findBoard.getId())
                        .param("title", "수정 제목 1")
                        .param("content", "수정내용 내용1234567890"))
                .andExpect(status().isOk())
                .andExpect(view().name("boards/Board"))
                .andExpect(model().attributeExists("board"));

        assertTrue(boardRepository.existsByTitle("수정 제목 1"));
        assertTrue(boardRepository.existsByContent("수정내용 내용1234567890"));
    }

    @DisplayName("글 수정 테스트 - 실패 : 글 존재 X")
    @Test
    void editFailCorrect() throws Exception{
        String testName = "테스트 제목 3";

        try{
            BoardResponseDto findBoard = boardService.findByTitle(testName);
            mockMvc.perform(post("/boards/{id}/edit", findBoard.getId())
                            .param("title", "수정 제목 1")
                            .param("content", "수정 내용 1"));
            }
        catch (IllegalArgumentException e){
            Assertions.assertEquals("해당 게시글이 없습니다. title =" + testName, e.getMessage());
        }
    }

    @DisplayName("글 삭제 테스트 - 성공")
    @Test
    void deleteFail() throws Exception{
        BoardResponseDto findBoard = boardService.findByTitle("테스트 제목 1");
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/boards/{id}/delete", findBoard.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/Boards"));
    }

    @DisplayName("글 삭제 테스트 - 실패 : 글 존재 X")
    @Test
    void delete() throws Exception{
        String testName = "테스트 제목 3";

        try {
            BoardResponseDto findBoard = boardService.findByTitle(testName);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/boards/{id}/delete", findBoard.getId()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/Boards"));
        }
        catch (IllegalArgumentException e){
            Assertions.assertEquals("해당 게시글이 없습니다. title =" + testName, e.getMessage());
        }
    }

}