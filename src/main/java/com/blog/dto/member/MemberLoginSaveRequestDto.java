package com.blog.dto.member;

import com.blog.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class MemberLoginSaveRequestDto {

    private String loginId; //로그인 ID

    private String password;

    public MemberLoginSaveRequestDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

}
