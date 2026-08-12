package com.typingspeedtest.api.result;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.typingspeedtest.api.result.dto.ResultRequest;
import com.typingspeedtest.api.result.dto.ResultResponse;
import com.typingspeedtest.api.user.User;
import com.typingspeedtest.api.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TypingResultService {

    private final TypingResultRepository resultRepository;
    private final UserRepository userRepository;

    public ResultResponse saveResult(
            ResultRequest request) {

        User user = getAuthenticatedUser();

        TypingResult result = TypingResult.builder()
                .user(user)
                .wpm(request.getWpm())
                .accuracy(request.getAccuracy())
                .correctCharacters(
                        request.getCorrectCharacters()
                )
                .incorrectCharacters(
                        request.getIncorrectCharacters()
                )
                .duration(request.getDuration())
                .passage(request.getPassage())
                .build();

        TypingResult savedResult =
                resultRepository.save(result);

        return mapToResponse(savedResult);
    }

    public List<ResultResponse> getMyResults() {

        User user = getAuthenticatedUser();

        return resultRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getName() == null) {

            throw new RuntimeException(
                    "User is not authenticated"
            );
        }

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found"
                        )
                );
    }

    private ResultResponse mapToResponse(
            TypingResult result) {

        return new ResultResponse(
                result.getId(),
                result.getWpm(),
                result.getAccuracy(),
                result.getCorrectCharacters(),
                result.getIncorrectCharacters(),
                result.getDuration(),
                result.getPassage(),
                result.getCreatedAt()
        );
    }
}