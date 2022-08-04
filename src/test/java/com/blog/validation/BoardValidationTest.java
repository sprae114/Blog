package com.blog.validation;

import com.blog.dto.board.BoardSaveRequestDto;
import com.blog.dto.board.BoardUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


public class BoardValidationTest {

    @DisplayName("BoardSaveRequestDto 제목 검증")
    @Test
    void boardValidationTitleFail(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        String shortTitle = "";
        String longTitle = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        String contents = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

        BoardSaveRequestDto boardSaveDtoShort = new BoardSaveRequestDto(shortTitle, contents);
        BoardSaveRequestDto boardSaveDtoLong = new BoardSaveRequestDto(longTitle, contents);

        //제목 길이 미만
        Set<ConstraintViolation<BoardSaveRequestDto>> validate = validator.validate(boardSaveDtoShort);
        for (ConstraintViolation<BoardSaveRequestDto> violation : validate) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("길이가 2에서 10 사이여야 합니다");
        }

        //제목 길이 초과
        Set<ConstraintViolation<BoardSaveRequestDto>> validate2 = validator.validate(boardSaveDtoLong);
        for (ConstraintViolation<BoardSaveRequestDto> violation : validate2) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("길이가 2에서 10 사이여야 합니다");
        }

    }

    @DisplayName("BoardSaveRequestDto 내용 검증")
    @Test
    void boardValidationContentFail(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        String Title = "AAAAAAAAA";
        String shortContents = "";
        String LongContents = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                              "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

        BoardSaveRequestDto boardSaveDtoShort = new BoardSaveRequestDto(Title, shortContents);
        BoardSaveRequestDto boardSaveDtoLong = new BoardSaveRequestDto(Title, LongContents);

        //내용 길이 미만
        Set<ConstraintViolation<BoardSaveRequestDto>> validate = validator.validate(boardSaveDtoShort);
        for (ConstraintViolation<BoardSaveRequestDto> violation : validate) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("길이가 10에서 100 사이여야 합니다");
        }

        //내용 길이 초과
        Set<ConstraintViolation<BoardSaveRequestDto>> validate2 = validator.validate(boardSaveDtoLong);
        for (ConstraintViolation<BoardSaveRequestDto> violation : validate2) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("길이가 10에서 100 사이여야 합니다");
        }
    }

    @DisplayName("BoardUpdateRequestDto 제목 검증")
    @Test
    void boardValidationTitleFail2(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        String shortTitle = "";
        String longTitle = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        String contents = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

        BoardUpdateRequestDto boardSaveDtoShort = new BoardUpdateRequestDto(shortTitle, contents);
        BoardUpdateRequestDto boardSaveDtoLong = new BoardUpdateRequestDto(longTitle, contents);

        //제목 길이 미만
        Set<ConstraintViolation<BoardUpdateRequestDto>> validate = validator.validate(boardSaveDtoShort);
        for (ConstraintViolation<BoardUpdateRequestDto> violation : validate) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("길이가 2에서 10 사이여야 합니다");
        }

        //제목 길이 초과
        Set<ConstraintViolation<BoardUpdateRequestDto>> validate2 = validator.validate(boardSaveDtoLong);
        for (ConstraintViolation<BoardUpdateRequestDto> violation : validate2) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("길이가 2에서 10 사이여야 합니다");
        }

    }

    @DisplayName("BoardUpdateRequestDto 내용 검증")
    @Test
    void boardValidationContentFail2(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        String Title = "AAAAAAAAA";
        String shortContents = "";
        String LongContents = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                              "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

        BoardUpdateRequestDto boardSaveDtoShort = new BoardUpdateRequestDto(Title, shortContents);
        BoardUpdateRequestDto boardSaveDtoLong = new BoardUpdateRequestDto(Title, LongContents);

        //내용 길이 미만
        Set<ConstraintViolation<BoardUpdateRequestDto>> validate = validator.validate(boardSaveDtoShort);
        for (ConstraintViolation<BoardUpdateRequestDto> violation : validate) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("길이가 10에서 100 사이여야 합니다");
        }

        //내용 길이 초과
        Set<ConstraintViolation<BoardUpdateRequestDto>> validate2 = validator.validate(boardSaveDtoLong);
        for (ConstraintViolation<BoardUpdateRequestDto> violation : validate2) {
            Assertions.assertThat(violation.getMessage()).isEqualTo("길이가 10에서 100 사이여야 합니다");
        }
    }


}
