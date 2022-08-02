package com.blog.controller;

import com.blog.domain.Board;
import com.blog.repository.BoardRepository;
import com.blog.service.BoardService;
import org.json.JSONString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

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
        Board board = new Board("테스트 제목1", "테스트 내용2");
        boardRepository.save(board);
    }

    @AfterEach
    void afterEach(){boardRepository.deleteAll();}


    @DisplayName("메인 페이지 조회 테스트")
    @Test
    void boards() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("Boards"));
    }


    @DisplayName("등록 페이지 조회 테스트")
    @Test
    void addForm() throws Exception{
        mockMvc.perform(get("/boards/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("boards/addForm"))
                .andExpect(model().attributeExists("board"));
    }


    @DisplayName("수정 페이지 조회 테스트")
    @Test
    void editForm() throws Exception{
        Board findBoard = boardRepository.findByTitle("테스트 제목1");

        mockMvc.perform(get("/boards/{id}/edit", findBoard.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("boards/editForm"))
                .andExpect(model().attributeExists("board"));
    }


    @DisplayName("상세 페이지 조회 테스트")
    @Test
    void board() throws Exception{
        Board findBoard = boardRepository.findByTitle("테스트 제목1");

        mockMvc.perform(get("/boards/{id}", findBoard.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("boards/Board"))
                .andExpect(model().attributeExists("board"));
    }



    @DisplayName("글 등록 테스트- 입력값 정상")
    @Test
    void testAddFormCorrect() throws Exception{
        mockMvc.perform(post("/boards/add")
                        .param("title", "테스트 제목")
                        .param("content", "테스트 내용")) //괄호위치
                .andExpect(status().is3xxRedirection()) //redirect 때문에
                .andExpect(view().name("redirect:/"));

        assertTrue(boardRepository.existsByTitle("테스트 제목"));
        assertTrue(boardRepository.existsByContent("테스트 내용"));
    }


    @DisplayName("글 등록 테스트- 중북된 제목")
    @Test
    void testAddFormRepeatName() throws Exception{
        mockMvc.perform(post("/boards/add")
                        .param("title", "테스트 제목1")
                        .param("content", "테스트 내용")) //괄호위치
                .andExpect(status().isOk()) //redirect 때문에
                .andExpect(view().name("/boards/addForm"));

        System.out.println(boardRepository.findAll());
    }


    @DisplayName("글 수정 테스트 - 성공")
    @Test
    void editCorrect() throws Exception{
        Board findBoard = boardRepository.findByTitle("테스트 제목1");

        mockMvc.perform(post("/boards/{id}/edit", findBoard.getId())
                        .param("title", "수정 제목1")
                        .param("content", "수정 내용1"))
                .andExpect(status().isOk())
                .andExpect(view().name("boards/Board"))
                .andExpect(model().attributeExists("board"));

        assertTrue(boardRepository.existsByTitle("수정 제목1"));
        assertTrue(boardRepository.existsByContent("수정 내용1"));
    }


    @DisplayName("글 삭제 테스트")
    @Test
    void delete() throws Exception{
        Board findBoard = boardRepository.findByTitle("테스트 제목1");

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/boards/{id}/delete", findBoard.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/Boards"));
    }

}