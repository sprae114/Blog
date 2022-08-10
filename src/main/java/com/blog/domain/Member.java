package com.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @GeneratedValue
    @Id
    private Long id;

    @NotEmpty
    private String loginId; //로그인 ID

    @NotEmpty
    private String name; //사용자 이름

    @NotEmpty
    private String password;

    @Builder
    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
