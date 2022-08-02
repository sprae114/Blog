package com.blog.controller;


import com.blog.domain.Board;
import com.blog.dto.BoardDto;
import com.blog.repository.BoardRepository;
import com.blog.service.BoardService;
import com.blog.validation.BoardValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;



@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardValidation boardValidation;
    private final BoardRepository boardRepository;
    private final BoardService boardService;


    @InitBinder("Board")
    public void initBinder(WebDataBinder webDataBinder) {
    webDataBinder.addValidators(boardValidation);
    }



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


    @PostMapping("/boards/add") //board 등록
    public String addForm(@ModelAttribute("board") BoardDto boardDto, RedirectAttributes redirectAttributes, Errors errors){

        if(errors.hasErrors()){
            return "/boards/addForm";
        }

        Board board = new Board(boardDto.getTitle(), boardDto.getContent());
        board.setUserId("A");
        board.setViewCnt(0);

        Board saveBoard = boardRepository.save(board);

        redirectAttributes.addAttribute("board", saveBoard.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/";
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



    @PostMapping("boards/{id}/edit")  //수정
    public String edit(@PathVariable Long id, @ModelAttribute Board board){
        BoardDto boardDto = new BoardDto(board.getTitle(), board.getContent());

        boardService.editBoard(id, boardDto);
        return "boards/Board";
    }



    @RequestMapping ("/boards/{id}/delete") //삭제
    public String delete(@PathVariable Long id){
        boardRepository.deleteById(id);
        return "redirect:/Boards";

    }

}