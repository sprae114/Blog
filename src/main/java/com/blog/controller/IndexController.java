package com.blog.controller;

import com.blog.domain.Board;
import com.blog.repository.BoardRepository;
import com.blog.service.BoardService;
import com.blog.validation.BoardValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final BoardValidation boardValidation;
    private final BoardRepository boardRepository;
    private final BoardService boardService;


    @GetMapping({"","/", "Boards"})//Board 조회
    public String boards(Model model){
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "Boards";
    }

    @GetMapping("/boards/add") //addForm 조회
    public String addForm(Model model){
        model.addAttribute("board", new Board());
        return "boards/addForm";
    }

    @GetMapping("boards/{id}") //상세페이지 조회
    public String board(@PathVariable Long id, Model model){
        Board board = boardRepository.findById(id).get();
        model.addAttribute("board", board);

        return "boards/Board";
    }

    @GetMapping("boards/{id}/edit") //수정페이지 조회
    public String editForm(@PathVariable Long id, Model model){
        Board board = boardRepository.findById(id).get();
        model.addAttribute("board", board);

        return "boards/editForm";
    }
}
