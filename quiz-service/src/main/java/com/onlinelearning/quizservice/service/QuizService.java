package com.onlinelearning.quizservice.service;

import com.onlinelearning.quizservice.entity.Quiz;
import com.onlinelearning.quizservice.repository.QuizRepository;
import com.onlinelearning.quizservice.dto.QuizRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    
    @Autowired
    private QuizRepository quizRepository;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public Quiz createQuiz(Long lessonId, List<QuizRequest.Question> questions) {
        try {
            String questionsJson = objectMapper.writeValueAsString(questions);
            Quiz quiz = new Quiz(lessonId, questionsJson);
            return quizRepository.save(quiz);
        } catch (Exception e) {
            throw new RuntimeException("Error creating quiz: " + e.getMessage());
        }
    }
    
    public Optional<Quiz> getQuizByLessonId(Long lessonId) {
        return quizRepository.findByLessonId(lessonId);
    }
    
    public Optional<Quiz> getQuizById(Long quizId) {
        return quizRepository.findById(quizId);
    }
    
    public int calculateScore(Long quizId, List<Integer> userAnswers) {
        Optional<Quiz> quizOpt = quizRepository.findById(quizId);
        if (quizOpt.isEmpty()) {
            throw new RuntimeException("Quiz not found");
        }
        
        try {
            Quiz quiz = quizOpt.get();
            List<QuizRequest.Question> questions = objectMapper.readValue(
                quiz.getQuestions(), 
                objectMapper.getTypeFactory().constructCollectionType(List.class, QuizRequest.Question.class)
            );
            
            int score = 0;
            for (int i = 0; i < Math.min(questions.size(), userAnswers.size()); i++) {
                if (questions.get(i).getCorrectAnswer().equals(userAnswers.get(i))) {
                    score++;
                }
            }
            return score;
        } catch (Exception e) {
            throw new RuntimeException("Error calculating score: " + e.getMessage());
        }
    }
}