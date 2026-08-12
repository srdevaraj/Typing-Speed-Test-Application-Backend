package com.typingspeedtest.api.result.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultResponse {

    private Long id;
    private Double wpm;
    private Double accuracy;
    private int correctCharacters;
    private int incorrectCharacters;
    private int duration;
    private String passage;
    private LocalDateTime createdAt;
}