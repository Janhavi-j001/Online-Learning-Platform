package com.onlinelearning.enrollmentservice.controller;

import com.onlinelearning.enrollmentservice.dto.*;
import com.onlinelearning.enrollmentservice.entity.Enrollment;
import com.onlinelearning.enrollmentservice.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    @PostMapping
    public ResponseEntity<?> enrollUser(
            @RequestBody EnrollmentRequest request,
            @RequestHeader("X-User-Id") Long userId) {
        try {
            Enrollment enrollment = enrollmentService.enrollUser(request.getCourseId(), userId);
            return ResponseEntity.ok(enrollment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Enrollment>> getUserEnrollments(@PathVariable Long userId) {
        List<Enrollment> enrollments = enrollmentService.getUserEnrollments(userId);
        return ResponseEntity.ok(enrollments);
    }
    
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Enrollment>> getCourseEnrollments(@PathVariable Long courseId) {
        List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseId);
        return ResponseEntity.ok(enrollments);
    }
    
    @GetMapping("/check/{courseId}/{userId}")
    public ResponseEntity<Boolean> checkEnrollment(@PathVariable Long courseId, @PathVariable Long userId) {
        boolean enrolled = enrollmentService.isUserEnrolled(courseId, userId);
        return ResponseEntity.ok(enrolled);
    }
    
    @PutMapping("/progress/{courseId}")
    public ResponseEntity<?> updateProgress(
            @PathVariable Long courseId,
            @RequestBody ProgressUpdateRequest request,
            @RequestHeader("X-User-Id") Long userId) {
        return enrollmentService.updateProgress(courseId, userId, request.getProgress())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}