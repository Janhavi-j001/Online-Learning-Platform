package com.onlinelearning.lessonservice.service;

import com.onlinelearning.lessonservice.entity.Lesson;
import com.onlinelearning.lessonservice.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    
    @Autowired
    private LessonRepository lessonRepository;
    
    public List<Lesson> getLessonsByCourse(Long courseId) {
        return lessonRepository.findByCourseIdOrderByOrderIndex(courseId);
    }
    
    public Optional<Lesson> getLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId);
    }
    
    public Lesson createLesson(Long courseId, String title, String content, String videoUrl, Integer orderIndex) {
        Lesson lesson = new Lesson(courseId, title, content, videoUrl, orderIndex);
        return lessonRepository.save(lesson);
    }
    
    public Optional<Lesson> updateLesson(Long lessonId, String title, String content, String videoUrl, Integer orderIndex) {
        Optional<Lesson> lessonOpt = lessonRepository.findById(lessonId);
        if (lessonOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Lesson lesson = lessonOpt.get();
        lesson.setTitle(title);
        lesson.setContent(content);
        lesson.setVideoUrl(videoUrl);
        lesson.setOrderIndex(orderIndex);
        return Optional.of(lessonRepository.save(lesson));
    }
    
    public boolean deleteLesson(Long lessonId) {
        if (lessonRepository.existsById(lessonId)) {
            lessonRepository.deleteById(lessonId);
            return true;
        }
        return false;
    }
}