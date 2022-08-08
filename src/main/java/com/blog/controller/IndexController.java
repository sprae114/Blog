package com.blog.controller;

import com.blog.domain.Board;
import com.blog.dto.member.MemberSaveRequestDto;
import com.blog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final BoardRepository boardRepository;

    /**
     * 게시판
     */
    @GetMapping({"","/", "Boards"})//Board 조회
    public String boards(Model model,
                         @PageableDefault(size = 4) Pageable pageable,
                         @RequestParam(required = false, defaultValue = "") String searchText){

//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContaining(searchText, pageable);

        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

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

    /**
     * 로그인
     */
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("member") MemberSaveRequestDto memberSaveRequestDto) {
        return "login/loginForm";
    }

    @GetMapping("/signup")
    public String signupForm(@ModelAttribute("member") MemberSaveRequestDto memberSaveRequestDto){
        return "login/addMemberForm";
    }
}
