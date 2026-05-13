package com.quiz.quiz_system.controller;

import com.quiz.quiz_system.dto.ApiResponse;
import com.quiz.quiz_system.dto.QuizResultDTO;
import com.quiz.quiz_system.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/result")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<QuizResultDTO>>> getResultsByUserId(@PathVariable Long userId) {
        List<QuizResultDTO> results = resultService.getResultsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<QuizResultDTO>>> getAllResults() {
        List<QuizResultDTO> results = resultService.getAllResults();
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @GetMapping("/quiz/{quizId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<QuizResultDTO>>> getResultsByQuizId(@PathVariable Long quizId) {
        List<QuizResultDTO> results = resultService.getResultsByQuizId(quizId);
        return ResponseEntity.ok(ApiResponse.success(results));
    }
}
