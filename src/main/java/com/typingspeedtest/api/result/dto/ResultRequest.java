package com.typingspeedtest.api.result.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultRequest {

    @NotNull(message = "WPM is required")
    @DecimalMin(
            value = "0.0",
            message = "WPM cannot be negative"
    )
    private Double wpm;

    @NotNull(message = "Accuracy is required")
    @DecimalMin(
            value = "0.0",
            message = "Accuracy cannot be below 0"
    )
    @DecimalMax(
            value = "100.0",
            message = "Accuracy cannot exceed 100"
    )
    private Double accuracy;

    @Min(
            value = 0,
            message = "Correct characters cannot be negative"
    )
    private int correctCharacters;

    @Min(
            value = 0,
            message = "Incorrect characters cannot be negative"
    )
    private int incorrectCharacters;

    @Min(
            value = 1,
            message = "Duration must be at least 1 second"
    )
    private int duration;

    @NotBlank(message = "Passage is required")
    private String passage;
}