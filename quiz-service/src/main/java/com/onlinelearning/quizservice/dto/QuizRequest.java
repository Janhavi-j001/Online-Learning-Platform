package com.onlinelearning.quizservice.dto;

import java.util.List;

public class QuizRequest {
    private Long lessonId;
    private List<Question> questions;
    
    public QuizRequest() {}
    
    public Long getLessonId() { return lessonId; }
    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }
    
    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
    
    public static class Question {
        private String question;
        private List<String> options;
        private Integer correctAnswer;
        
        public Question() {}
        
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        
        public List<String> getOptions() { return options; }
        public void setOptions(List<String> options) { this.options = options; }
        
        public Integer getCorrectAnswer() { return correctAnswer; }
        public void setCorrectAnswer(Integer correctAnswer) { this.correctAnswer = correctAnswer; }
    }
}