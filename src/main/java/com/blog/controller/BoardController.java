package com.blog.controller;


import com.blog.dto.board.BoardResponseDto;
import com.blog.dto.board.BoardSaveRequestDto;
import com.blog.dto.board.BoardUpdateRequestDto;
import com.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


    @PostMapping("/boards/add")
    public String addForm(@Valid @ModelAttribute("board") BoardSaveRequestDto requestDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "boards/addForm";
        }

        boardService.save(requestDto);
        return "redirect:/";
    }

    @PostMapping("/boards/{id}/edit")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("board") BoardUpdateRequestDto boardUpdateDto,
                       BindingResult bindingResult,
                       Model model){

        if(bindingResult.hasErrors()){
            return "boards/editForm";
        }

        Long updateId = boardService.update(id, boardUpdateDto);
        BoardResponseDto saveBoard = boardService.findById(updateId);
        model.addAttribute("board" , saveBoard);

        return "boards/Board";
    }

    @RequestMapping ("/boards/{id}/delete")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/Boards";
    }

}