package com.onlinelearning.quizservice.dto;

import java.util.List;

public class QuizSubmissionRequest {
    private Long quizId;
    private List<Integer> answers;
    
    public QuizSubmissionRequest() {}
    
    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }
    
    public List<Integer> getAnswers() { return answers; }
    public void setAnswers(List<Integer> answers) { this.answers = answers; }
}