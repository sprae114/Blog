package com.blog.service;

import com.blog.domain.Member;
import com.blog.dto.board.BoardSaveRequestDto;
import com.blog.dto.member.MemberSaveRequestDto;
import com.blog.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach(){
        MemberSaveRequestDto saveRequestDto = MemberSaveRequestDto.builder()
                .loginId("abcd123")
                .name("김철수")
                .password("abcd1234")
                .build();


        try {
            loginService.save(saveRequestDto);
        }

        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void afterEach(){memberRepository.deleteAll();}

    @DisplayName("회원가입 - 정상")
    @Test
    void save() {
        MemberSaveRequestDto saveRequestDto = MemberSaveRequestDto.builder()
                .loginId("abc123")
                .name("홍길동")
                .password("qwer1234")
                .build();


        try {
            Long id = loginService.save(saveRequestDto);
            Member findMember = memberRepository.findById(id).get();

            assertThat(saveRequestDto.getLoginId()).isEqualTo(findMember.getLoginId());
            assertThat(saveRequestDto.getName()).isEqualTo(findMember.getName());
            assertThat(saveRequestDto.getPassword()).isEqualTo(findMember.getPassword());
        }

        catch (Exception e){
            throw new RuntimeException(e);
        }

    }


    @DisplayName("로그인 - 정상")
    @Test
    void loginCorrect() {
        MemberSaveRequestDto saveRequestDto = MemberSaveRequestDto.builder()
                .loginId("abcd123")
                .name("김철수")
                .password("abcd1234")
                .build();
        try {
            Member loginMember = loginService.login(saveRequestDto.getLoginId(), saveRequestDto.getPassword());
            assertThat(loginMember.getLoginId()).isEqualTo(saveRequestDto.getLoginId());
            String hashingPassword = loginService.get_password(saveRequestDto.getLoginId(),
                    saveRequestDto.getPassword().getBytes(StandardCharsets.UTF_8));
            assertThat(loginMember.getPassword()).isEqualTo(hashingPassword);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }


    @DisplayName("로그인 - 비번 오류")
    @Test
    void loginFailPassword() {
        MemberSaveRequestDto saveRequestDto = MemberSaveRequestDto.builder()
                .loginId("abcd123")
                .name("김철수")
                .password("1234")
                .build();


        try {
            Member saveMember = loginService.login(saveRequestDto.getLoginId(), saveRequestDto.getPassword());
            assertThat(saveMember).isNull();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }


    @DisplayName("로그인 - 아이디 오류")
    @Test
    void loginFailLoginId() {
        MemberSaveRequestDto saveRequestDto = MemberSaveRequestDto.builder()
                .loginId("abc123")
                .name("김철수")
                .password("1234")
                .build();


        try {
            Member saveMember = loginService.login(saveRequestDto.getLoginId(), saveRequestDto.getPassword());
            assertThat(saveMember).isEqualTo(null);
        }

        catch (Exception e){
            org.junit.jupiter.api.Assertions.assertEquals("No value present", e.getMessage());
        }

    }
}