package com.onlinelearning.lessonservice.controller;

import com.onlinelearning.lessonservice.dto.LessonRequest;
import com.onlinelearning.lessonservice.entity.Lesson;
import com.onlinelearning.lessonservice.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {
    
    @Autowired
    private LessonService lessonService;
    
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Lesson>> getLessonsByCourse(@PathVariable Long courseId) {
        List<Lesson> lessons = lessonService.getLessonsByCourse(courseId);
        return ResponseEntity.ok(lessons);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Lesson> createLesson(@RequestBody LessonRequest request) {
        Lesson lesson = lessonService.createLesson(
            request.getCourseId(),
            request.getTitle(),
            request.getContent(),
            request.getVideoUrl(),
            request.getOrderIndex()
        );
        return ResponseEntity.ok(lesson);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long id, @RequestBody LessonRequest request) {
        return lessonService.updateLesson(id, request.getTitle(), request.getContent(), 
                request.getVideoUrl(), request.getOrderIndex())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long id) {
        boolean deleted = lessonService.deleteLesson(id);
        if (deleted) {
            return ResponseEntity.ok("Lesson deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}