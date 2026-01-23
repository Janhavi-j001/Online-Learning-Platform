package com.onlinelearning.quizservice.repository;

import com.onlinelearning.quizservice.entity.Quiz;
import com.onlinelearning.quizservice.entity.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByLessonId(Long lessonId);
}

@Repository
interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {
    Optional<QuizSubmission> findByQuizIdAndUserId(Long quizId, Long userId);
}