package com.blog.dto.member;

import com.blog.domain.Board;
import com.blog.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class MemberSaveRequestDto {


    @Pattern(regexp = "^[a-zA-Z0-9]{2,10}$", message = "2 ~ 10글자 영어와 숫자로만 구성해주세요.")
    private String loginId; //로그인 ID


    @Pattern(regexp = "[가-힣]{2,4}$", message = "2 ~ 4글자 한글로만 구성해주세요.")
    private String name; //사용자 이름

    @Length(min = 2, max= 10)
    private String password;

    @Builder
    public MemberSaveRequestDto(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }

    public Member toEntity(){
        return Member.builder()
                .loginId(loginId)
                .name(name)
                .password(password)
                .build();
    }
}
