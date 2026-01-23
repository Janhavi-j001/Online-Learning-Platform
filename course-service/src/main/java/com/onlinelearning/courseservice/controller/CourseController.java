package com.onlinelearning.courseservice.controller;

import com.onlinelearning.courseservice.dto.*;
import com.onlinelearning.courseservice.entity.Course;
import com.onlinelearning.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping
    public ResponseEntity<Page<Course>> getCourses(
            @RequestParam(required = false) String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Course> courses = courseService.getAllPublishedCourses(tag, page, size);
        return ResponseEntity.ok(courses);
    }
    
    @PostMapping
    public ResponseEntity<?> createCourse(
            @RequestBody CourseRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long instructorId) {
        try {
            Course course = courseService.createCourse(
                instructorId,
                request.getTitle(),
                request.getDescription(),
                request.getTags()
            );
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long instructorId) {
        try {
            return courseService.updateCourse(id, instructorId, request.getTitle(), request.getDescription(), request.getTags())
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/{id}/publish")
    public ResponseEntity<?> publishCourse(@PathVariable Long id) {
        boolean published = courseService.publishCourse(id);
        if (published) {
            return ResponseEntity.ok("Course published successfully");
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable Long instructorId) {
        List<Course> courses = courseService.getCoursesByInstructor(instructorId);
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}