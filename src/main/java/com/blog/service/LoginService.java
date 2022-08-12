package com.blog.service;

import com.blog.domain.Board;
import com.blog.domain.Member;
import com.blog.dto.board.BoardResponseDto;
import com.blog.dto.board.BoardSaveRequestDto;
import com.blog.dto.member.MemberSaveRequestDto;
import com.blog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

import static com.blog.dto.member.MemberSaveRequestDto.*;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;


    @Transactional
    public Long save(MemberSaveRequestDto requestDto) throws Exception{
        String salt = getSALT();
        String hashingPassword = Hashing(requestDto.getPassword().getBytes(StandardCharsets.UTF_8), salt);

        requestDto.setSalt(salt);
        requestDto.setPassword(hashingPassword);

        return memberRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Member login(String loginId, String password) throws Exception{
        String hashingPassword = get_password(loginId, password.getBytes(StandardCharsets.UTF_8));

        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(hashingPassword))
                .orElse(null);
    }

    public String get_password(String loginId, byte[] password) throws Exception {
        Member member = memberRepository.findByLoginId(loginId).get();
        String temp_salt = member.getSalt();       // 해당 ID의 SALT 값을 찾는다

        return Hashing(password, temp_salt);	// 얻어온 Salt 와 password 를 조합해본다.
    }
}
