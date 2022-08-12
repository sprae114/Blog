package com.blog.dto.member;

import com.blog.domain.Board;
import com.blog.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.MessageDigest;
import java.security.SecureRandom;

@Getter
@Setter
@NoArgsConstructor
public class MemberSaveRequestDto {
    private static final int SALT_SIZE = 16;


    @Pattern(regexp = "^[a-zA-Z0-9]{2,10}$", message = "2 ~ 10글자 영어와 숫자로만 구성해주세요.")
    private String loginId; //로그인 ID


    @Pattern(regexp = "[가-힣]{2,4}$", message = "2 ~ 4글자 한글로만 구성해주세요.")
    private String name; //사용자 이름


    @Length(min = 2, max= 10)
    private String password;

    private String salt;

    @Builder
    public MemberSaveRequestDto(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .password(password)
                .loginId(loginId)
                .salt(salt)
                .build();
    }

    // 비밀번호 해싱
    static public String Hashing(byte[] password, String Salt) throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-256");	// SHA-256 해시함수를 사용

        for(int i = 0; i < 10000; i++) {
            String temp = Byte_to_String(password) + Salt;	// 패스워드와 Salt 를 합쳐 새로운 문자열 생성
            md.update(temp.getBytes());						// temp 의 문자열을 해싱하여 md 에 저장해둔다
            password = md.digest();							// md 객체의 다이제스트를 얻어 password 를 갱신한다
        }

        return Byte_to_String(password);
    }


    // SALT 값 생성
    static public String getSALT() throws Exception {
        SecureRandom rnd = new SecureRandom();
        byte[] temp = new byte[SALT_SIZE];
        rnd.nextBytes(temp);

        return Byte_to_String(temp);

    }


    // 바이트 값을 16진수로 변경해준다
    static public String Byte_to_String(byte[] temp) {
        StringBuilder sb = new StringBuilder();
        for(byte a : temp) {
            sb.append(String.format("%02x", a));
        }
        return sb.toString();
    }
}
