package com.blog.controller;

import com.blog.domain.Board;
import com.blog.domain.Member;
import com.blog.dto.member.MemberSaveRequestDto;
import com.blog.repository.MemberRepository;
import com.blog.service.LoginService;
import org.assertj.core.api.Assertions;
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

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
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
        MemberSaveRequestDto member = new MemberSaveRequestDto("abcd1234", "홍길동", "qwer1234");
        try {
            loginService.save(member);
        }
        catch (Exception e){
            new RuntimeException(e);
        }
    }

    @AfterEach
    void afterEach(){memberRepository.deleteAll();}



    @DisplayName("로그인 처리 - 성공")
    @Test
    void login() throws Exception{
        String loginId = "abcd1234";
        String loginPassword = "qwer1234";

        String hashingPassword = loginService.get_password(loginId,
                loginPassword.getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(post("/login")
                        .param("loginId", loginId)
                        .param("password", hashingPassword));

        Member saveMember = memberRepository.findByLoginId(loginId).get();
        assertNotNull(saveMember);
        assertEquals(saveMember.getPassword(), hashingPassword);
    }

    @DisplayName("로그인 처리 - 실패 : 아이디가 없는경우")
    @Test
    void loginLoginEmpty() throws Exception{
        mockMvc.perform(post("/login")
                        .param("loginId", "ABC1234")
                        .param("password", "qwer1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/loginForm"));

        assertThat(memberRepository.findByLoginId("ABC1234")).isEmpty();
    }


    @DisplayName("로그인 처리 - 실패 : 비번이 일치하지 않는 경우")
    @Test
    void loginLoginIdPassword() throws Exception{
        mockMvc.perform(post("/login")
                        .param("loginId", "abcd1234")
                        .param("password", "qw1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/loginForm"));

        Member saveMember = memberRepository.findByLoginId("abcd1234").get();
        assertNotNull(saveMember);
        assertThat(saveMember.getPassword()).isNotEqualTo("qw1234");
    }


    @DisplayName("회원 가입 처리 - 성공")
    @Test
    void save() throws Exception{
        mockMvc.perform(post("/signup")
                        .param("loginId", "abcd12345")
                        .param("name", "김철수")
                        .param("password", "rewq1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        Member saveMember = memberRepository.findByLoginId("abcd12345").get();
        assertEquals(saveMember.getLoginId(), "abcd12345");
        assertEquals(saveMember.getName(), "김철수");

        String hashingPassword = loginService.get_password("abcd12345",
                "rewq1234".getBytes(StandardCharsets.UTF_8));

        assertEquals(saveMember.getPassword(), hashingPassword);
    }


    @DisplayName("회원 가입 처리 - 실패 : 기존 아이디가 있는 경우")
    @Test
    void saveDobuleId() throws Exception{
        mockMvc.perform(post("/signup")
                        .param("loginId", "abcd1234")
                        .param("name", "김철수")
                        .param("password", "rewq1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/addMemberForm"));

        assertThat(memberRepository.findByLoginId("abcd1234")).isPresent();
    }


    @DisplayName("로그아웃")
    @Test
    void logout() throws Exception{
        mockMvc.perform(post("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}