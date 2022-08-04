package com.blog.repository;

import com.blog.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsByTitle(String title);

    boolean existsByContent(String content);

    Optional<Board> findByTitle(String title);
}
