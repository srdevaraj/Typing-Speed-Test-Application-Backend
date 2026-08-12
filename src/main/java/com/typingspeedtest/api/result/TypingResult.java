package com.typingspeedtest.api.result;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;

import com.typingspeedtest.api.user.User;

@Entity
@Table(name = "typing_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypingResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Column(nullable = false)
    private double wpm;

    @Column(nullable = false)
    private double accuracy;

    @Column(nullable = false)
    private int correctCharacters;

    @Column(nullable = false)
    private int incorrectCharacters;

    @Column(nullable = false)
    private int duration;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String passage;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
    }
}