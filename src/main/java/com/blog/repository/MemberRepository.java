package com.blog.repository;

import com.blog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByLoginId(String LoginId);

    boolean existsByPassword(String Password);

    Optional<Member> findByLoginId(String loginId);


}
