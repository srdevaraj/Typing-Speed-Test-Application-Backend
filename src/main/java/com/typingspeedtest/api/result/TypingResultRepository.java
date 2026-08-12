package com.typingspeedtest.api.result;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypingResultRepository
        extends JpaRepository<TypingResult, Long> {

    List<TypingResult>
    findByUserIdOrderByCreatedAtDesc(Long userId);
}