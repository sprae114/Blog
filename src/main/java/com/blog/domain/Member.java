package com.blog.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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

    private String salt;


    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
    @Builder
    public Member(String loginId, String name, String password, String salt) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.salt = salt;
    }
}
