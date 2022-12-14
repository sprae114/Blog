package com.blog.controller;


import com.blog.dto.board.BoardResponseDto;
import com.blog.dto.board.BoardSaveRequestDto;
import com.blog.dto.board.BoardUpdateRequestDto;
import com.blog.dto.reply.ReplySaveRequestDto;
import com.blog.service.BoardService;
import com.blog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.Session;


@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final ReplyService replyService;


    @PostMapping("/boards/add")
    public String addForm(@Valid @ModelAttribute("board") BoardSaveRequestDto requestDto,
                          BindingResult bindingResult,
                          HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "boards/addForm";
        }
        Object loginId = request.getSession().getAttribute("loginId");
        String saveLoginId = String.valueOf(loginId);

        boardService.save(requestDto, saveLoginId);
        return "redirect:/";
    }

    @PostMapping("/boards/{id}/edit")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("board") BoardUpdateRequestDto boardUpdateDto,
                       BindingResult bindingResult,
                       ReplySaveRequestDto requestDto,
                       Model model){

        if(bindingResult.hasErrors()){
            return "boards/editForm";
        }

        Long updateId = boardService.update(id, boardUpdateDto);
        BoardResponseDto saveBoard = boardService.findById(updateId);
        model.addAttribute("board" , saveBoard);

        return "redirect:/" + "boards/{id}";
    }

    @RequestMapping ("/boards/{id}/delete")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        replyService.deleteBoard(id);
        return "redirect:/Boards";
    }

}