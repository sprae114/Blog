package com.blog.controller;


import com.blog.domain.Board;
import com.blog.dto.BoardDto;
import com.blog.dto.BoardSaveRequestDto;
import com.blog.dto.BoardUpdateRequestDto;
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

    @PostMapping("/boards/add")
    public String addForm(@ModelAttribute("board") BoardSaveRequestDto requestDto){
        boardService.save(requestDto);
        return "redirect:/";
    }

    @PostMapping("boards/{id}/edit")  //board수정
    public String edit(@PathVariable Long id, @ModelAttribute("board") BoardUpdateRequestDto boardUpdateDto){
        boardService.update(id, boardUpdateDto);
        return "boards/Board";
    }

    @RequestMapping ("/boards/{id}/delete") //board삭제
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/Boards";
    }
}