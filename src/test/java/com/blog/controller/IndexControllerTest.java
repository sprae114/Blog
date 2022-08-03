package com.blog.controller;

import com.blog.domain.Board;
import com.blog.dto.BoardResponseDto;
import com.blog.dto.BoardSaveRequestDto;
import com.blog.repository.BoardRepository;
import com.blog.service.BoardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class IndexControllerTest {
    @Autowired
    MockMvc mockMvc;  //@AutoConfigureMockMvc를 추가해야함.

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    void beforeEach() {
        BoardSaveRequestDto boardSaveDto = new BoardSaveRequestDto("테스트 제목 1", "테스트 내용 1");
        boardService.save(boardSaveDto);
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
        BoardResponseDto findBoard = boardService.findByTitle("테스트 제목 1");

        mockMvc.perform(get("/boards/{id}/edit", findBoard.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("boards/editForm"))
                .andExpect(model().attributeExists("board"));
    }

    @DisplayName("상세 페이지 조회 테스트")
    @Test
    void board() throws Exception{
        BoardResponseDto findBoard = boardService.findByTitle("테스트 제목 1");

        mockMvc.perform(get("/boards/{id}", findBoard.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("boards/Board"))
                .andExpect(model().attributeExists("board"));
    }
}