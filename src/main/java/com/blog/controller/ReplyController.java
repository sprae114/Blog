package com.blog.controller;

import com.blog.dto.reply.ReplySaveRequestDto;
import com.blog.repository.ReplyRepository;
import com.blog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyRepository replyRepository;

    private final ReplyService replyService;

    @RequestMapping("/boards/{id}/addReply")
    public String addReply(@PathVariable Long id,
                           ReplySaveRequestDto replySaveRequestDto,
                           HttpServletRequest request){

        Object loginId = request.getSession().getAttribute("loginId");
        String saveLoginId = String.valueOf(loginId);

        replySaveRequestDto.setMemberId(saveLoginId);
        replySaveRequestDto.setBoardId(id);

        replyService.saveReply(replySaveRequestDto);

        return "redirect:/" + "boards/{id}";
    }

    @RequestMapping("/boards/{id}/delete/{deleteId}")
    public String deleteReply(@PathVariable Long id, @PathVariable Long deleteId){
        replyService.deleteReply(deleteId);
        return "redirect:/" + "boards/{id}";
    }

}
