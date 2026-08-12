package com.typingspeedtest.api.result;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.typingspeedtest.api.result.dto.ResultRequest;
import com.typingspeedtest.api.result.dto.ResultResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class TypingResultController {

    private final TypingResultService resultService;

    @PostMapping
    public ResponseEntity<ResultResponse> saveResult(
            @Valid @RequestBody ResultRequest request) {

        ResultResponse response =
                resultService.saveResult(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResultResponse>> getMyResults() {

        return ResponseEntity.ok(
                resultService.getMyResults()
        );
    }
}