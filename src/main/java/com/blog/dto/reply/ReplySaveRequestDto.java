package com.blog.dto.reply;

import com.blog.domain.Reply;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplySaveRequestDto {

    @Length(min = 2 , max = 30)
    private String content;

    private String memberId;

    private Long boardId;

    public Reply toEntity() {   //요청에서 받은 후 entity로 변환해주기 위함.
        return Reply.builder()
                .content(content)
                .memberId(memberId)
                .boardId(boardId)
                .build();
    }
}
