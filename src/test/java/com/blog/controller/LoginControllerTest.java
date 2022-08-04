package com.blog.controller;

import com.blog.domain.Board;
import com.blog.domain.Member;
import com.blog.repository.MemberRepository;
import com.blog.service.LoginService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LoginService loginService;

    @BeforeEach
    void beforeEach() {
        Member member = new Member("테스트아이디1", "홍길동", "qwer1234");
        memberRepository.save(member);
    }

    @AfterEach
    void afterEach(){memberRepository.deleteAll();}



    @DisplayName("로그인 처리 - 로그인 성공")
    @Test
    void login() throws Exception{
        mockMvc.perform(post("/login")
                        .param("loginId", "테스트아이디1")
                        .param("password", "qwer1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        Member saveMember = memberRepository.findByLoginId("테스트아이디1").get();
        assertNotNull(saveMember);
        assertEquals(saveMember.getPassword(), "qwer1234");
    }

    @DisplayName("회원 가입 처리 - 가입 성공")
    @Test
    void save() throws Exception{
        mockMvc.perform(post("/signup")
                        .param("loginId", "테스트아이디2")
                        .param("name", "김철수")
                        .param("password", "rewq1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        Member saveMember = memberRepository.findByLoginId("테스트아이디2").get();
        assertEquals(saveMember.getLoginId(), "테스트아이디2");
        assertEquals(saveMember.getName(), "김철수");
        assertEquals(saveMember.getPassword(), "rewq1234");
    }

    @DisplayName("로그아웃")
    @Test
    void logout() throws Exception{
        mockMvc.perform(post("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}