package com.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply extends BaseTimeEntity{
    @GeneratedValue
    @Id
    private Long id;

    @Column
    private String memberId;

    @Column(nullable = false)
    private String content;

    private Long boardId;

    public Reply(String memberId, String content, Long boardId) {
        this.memberId = memberId;
        this.content = content;
        this.boardId = boardId;
    }
}
