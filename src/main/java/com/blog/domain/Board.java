package com.blog.domain;

import com.blog.dto.board.BoardUpdateRequestDto;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board extends BaseTimeEntity{

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    private Member memberId;

    private String userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Builder
    public Board(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    @Builder
    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateBoard(BoardUpdateRequestDto boardUpdateRequestDto){
        this.title = boardUpdateRequestDto.getTitle();
        this.content = boardUpdateRequestDto.getContent();
    }
}