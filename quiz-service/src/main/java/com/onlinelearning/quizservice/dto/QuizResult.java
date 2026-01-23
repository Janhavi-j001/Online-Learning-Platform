package com.onlinelearning.quizservice.dto;

public class QuizResult {
    private Integer score;
    private Integer totalQuestions;
    private Double percentage;
    
    public QuizResult(Integer score, Integer totalQuestions) {
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = totalQuestions > 0 ? (score * 100.0) / totalQuestions : 0.0;
    }
    
    public Integer getScore() { return score; }
    public Integer getTotalQuestions() { return totalQuestions; }
    public Double getPercentage() { return percentage; }
}