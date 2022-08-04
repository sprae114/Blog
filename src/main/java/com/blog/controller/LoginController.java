package com.blog.controller;

import com.blog.domain.Member;
import com.blog.dto.member.MemberSaveRequestDto;
import com.blog.repository.MemberRepository;
import com.blog.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final MemberRepository memberRepository;
    private final LoginService loginService;


    @PostMapping ("/login")
    public String login(@Valid @ModelAttribute("member") MemberSaveRequestDto memberSaveRequestDto,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(memberSaveRequestDto.getLoginId(), memberSaveRequestDto.getPassword());

        log.info("{}가 로그인 되었습니다", memberSaveRequestDto.getLoginId());
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);

        return "redirect:" + redirectURL;
    }


    @PostMapping("/signup")
    public String addMember(@Valid @ModelAttribute("member") MemberSaveRequestDto memberSaveRequestDto,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "login/addMemberForm";
        }

        loginService.save(memberSaveRequestDto);
        log.info("{}가 회원가입 되었습니다", memberSaveRequestDto.getLoginId());
        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        log.info("로그아웃 되었습니다");

        return "redirect:/";
    }
}
