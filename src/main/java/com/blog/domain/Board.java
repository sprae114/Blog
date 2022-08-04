package com.blog.domain;

import com.blog.dto.BoardUpdateRequestDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter //없애야함
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board extends BaseTimeEntity{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column
    private String userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

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