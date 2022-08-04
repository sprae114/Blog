package com.blog.controller;


import com.blog.dto.BoardResponseDto;
import com.blog.dto.BoardSaveRequestDto;
import com.blog.dto.BoardUpdateRequestDto;
import com.blog.repository.BoardRepository;
import com.blog.service.BoardService;
//import com.blog.validation.BoardValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class BoardController {

//    private final BoardValidation boardValidation;
    private final BoardRepository boardRepository;
    private final BoardService boardService;

//
//    @InitBinder("Board")
//    public void initBinder(WebDataBinder webDataBinder) {
//    webDataBinder.addValidators(boardValidation);
//    }

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