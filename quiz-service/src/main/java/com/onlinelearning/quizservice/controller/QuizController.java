package com.onlinelearning.quizservice.controller;

import com.onlinelearning.quizservice.dto.*;
import com.onlinelearning.quizservice.entity.Quiz;
import com.onlinelearning.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    
    @Autowired
    private QuizService quizService;
    
    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizRequest request) {
        try {
            Quiz quiz = quizService.createQuiz(request.getLessonId(), request.getQuestions());
            return ResponseEntity.ok(quiz);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<Quiz> getQuizByLessonId(@PathVariable Long lessonId) {
        return quizService.getQuizByLessonId(lessonId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/submit")
    public ResponseEntity<QuizResult> submitQuiz(
            @RequestBody QuizSubmissionRequest request,
            @RequestHeader("X-User-Id") Long userId) {
        try {
            int score = quizService.calculateScore(request.getQuizId(), request.getAnswers());
            QuizResult result = new QuizResult(score, request.getAnswers().size());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}