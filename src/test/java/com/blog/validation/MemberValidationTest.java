package com.blog.validation;

import com.blog.domain.Member;
import com.blog.dto.member.MemberLoginSaveRequestDto;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberValidationTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LoginService loginService;

    @BeforeEach
    void beforeEach() {
        MemberSaveRequestDto saveMember = new MemberSaveRequestDto("abcd1234", "홍길동", "qwer1234");
        loginService.save(saveMember);
    }
    @AfterEach
    void afterEach(){memberRepository.deleteAll();}


    @DisplayName("회원가입 - 중복된 ID")
    @Test
    void memberValidationDuplicateID(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        String LoginId = "abcd1234";
        String name = "홍길동";
        String password = "qwer1234";

        MemberSaveRequestDto saveMemberDto = new MemberSaveRequestDto(LoginId, name, password);

        //중복된 ID
        Set<ConstraintViolation<MemberSaveRequestDto>> validate1 = validator.validate(saveMemberDto);
        for (ConstraintViolation<MemberSaveRequestDto> violation : validate1) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("");
        }
    }

    @DisplayName("회원가입 - 아이디 검증 : 길이")
    @Test
    void memberValidationLoginIDLength(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        String shortLoginId = "";
        String longLoginId = "AAAAAAAAAAAAAAAAAAAAAAA";
        String name = "홍길동";
        String password = "qwer1234";

        MemberSaveRequestDto saveMemberDtoShort = new MemberSaveRequestDto(shortLoginId, name, password);
        MemberSaveRequestDto saveMemberDtoLong = new MemberSaveRequestDto(longLoginId, name, password);

        //아이디 길이 미만
        Set<ConstraintViolation<MemberSaveRequestDto>> validate1 = validator.validate(saveMemberDtoShort);
        for (ConstraintViolation<MemberSaveRequestDto> violation : validate1) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("2 ~ 10글자 영어와 숫자로만 구성해주세요.");

        }

        //아이디 길이 이상
        Set<ConstraintViolation<MemberSaveRequestDto>> validate2 = validator.validate(saveMemberDtoLong);
        for (ConstraintViolation<MemberSaveRequestDto> violation : validate2) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("2 ~ 10글자 영어와 숫자로만 구성해주세요.");
        }
    }

    @DisplayName("회원가입 - 아이디 검증 : 틀린 문자")
    @Test
    void memberValidationLoginIDChar(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        String LoginId = "ㅂㅈㄷㄱ132";
        String name = "홍길동";
        String password = "qwer1234";

        MemberSaveRequestDto saveMemberDto = new MemberSaveRequestDto(LoginId, name, password);


        Set<ConstraintViolation<MemberSaveRequestDto>> validate1 = validator.validate(saveMemberDto);
        for (ConstraintViolation<MemberSaveRequestDto> violation : validate1) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("2 ~ 10글자 영어와 숫자로만 구성해주세요.");
        }
    }

    @DisplayName("회원가입 - 이름 검증 : 길이")
    @Test
    void memberValidationNameLength(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        String LoginId = "AAA123";
        String nameShort = "";
        String nameLong = "가나다라마";
        String password = "qwer1234";

        MemberSaveRequestDto saveMemberDtoShort = new MemberSaveRequestDto(LoginId, nameShort, password);
        MemberSaveRequestDto saveMemberDtoLong = new MemberSaveRequestDto(LoginId, nameLong, password);

        //이름 길이 미만
        Set<ConstraintViolation<MemberSaveRequestDto>> validate1 = validator.validate(saveMemberDtoShort);
        for (ConstraintViolation<MemberSaveRequestDto> violation : validate1) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("2 ~ 4글자 한글로만 구성해주세요.");
        }

        //이름 길이 이상
        Set<ConstraintViolation<MemberSaveRequestDto>> validate2 = validator.validate(saveMemberDtoLong);
        for (ConstraintViolation<MemberSaveRequestDto> violation : validate2) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("2 ~ 4글자 한글로만 구성해주세요.");
        }
    }

    @DisplayName("회원가입 - 이름 검증 : 틀린 문자")
    @Test
    void memberValidationNameChar(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        String LoginId = "AAA123";
        String nameShort = "asdas";
        String password = "qwer1234";

        MemberSaveRequestDto saveMemberDtoShort = new MemberSaveRequestDto(LoginId, nameShort, password);

        //틀린문자
        Set<ConstraintViolation<MemberSaveRequestDto>> validate1 = validator.validate(saveMemberDtoShort);
        for (ConstraintViolation<MemberSaveRequestDto> violation : validate1) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("2 ~ 4글자 한글로만 구성해주세요.");
        }
    }

    @DisplayName("회원가입 - 비번 검증 : 길이")
    @Test
    void memberValidationPassWordLength(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        String LoginId = "AAA123";
        String name = "홍길동";
        String passwordShort = "q";
        String passwordLong = "가나다라마바사아자아123513";


        MemberSaveRequestDto saveMemberDtoShort = new MemberSaveRequestDto(LoginId, name, passwordShort);
        MemberSaveRequestDto saveMemberDtoLong = new MemberSaveRequestDto(LoginId, name, passwordLong);

        //비번 길이 미만
        Set<ConstraintViolation<MemberSaveRequestDto>> validate1 = validator.validate(saveMemberDtoShort);
        for (ConstraintViolation<MemberSaveRequestDto> violation : validate1) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("길이가 2에서 10 사이여야 합니다");
        }

        //비번 길이 이상
        Set<ConstraintViolation<MemberSaveRequestDto>> validate2 = validator.validate(saveMemberDtoLong);
        for (ConstraintViolation<MemberSaveRequestDto> violation : validate2) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("길이가 2에서 10 사이여야 합니다");
        }
    }


}
