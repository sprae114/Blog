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

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSaveRequestDto requestDto){
        return memberRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
