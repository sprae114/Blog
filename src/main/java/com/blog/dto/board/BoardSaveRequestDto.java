package com.blog.dto.board;

import com.blog.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class BoardSaveRequestDto {

    @Length(min = 2 , max = 10)
    private String title;
    @Length(min = 10 , max = 100)
    private String content;


    public BoardSaveRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Board toEntity() {   //요청에서 받은 후 entity로 변환해주기 위함.
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }
}
