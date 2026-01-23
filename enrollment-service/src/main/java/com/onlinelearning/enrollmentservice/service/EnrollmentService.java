package com.onlinelearning.enrollmentservice.service;

import com.onlinelearning.enrollmentservice.entity.Enrollment;
import com.onlinelearning.enrollmentservice.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    public Enrollment enrollUser(Long courseId, Long userId) {
        if (enrollmentRepository.existsByCourseIdAndUserId(courseId, userId)) {
            throw new RuntimeException("User already enrolled in this course");
        }
        
        Enrollment enrollment = new Enrollment(courseId, userId);
        return enrollmentRepository.save(enrollment);
    }
    
    public List<Enrollment> getUserEnrollments(Long userId) {
        return enrollmentRepository.findByUserId(userId);
    }
    
    public List<Enrollment> getCourseEnrollments(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }
    
    public boolean isUserEnrolled(Long courseId, Long userId) {
        return enrollmentRepository.existsByCourseIdAndUserId(courseId, userId);
    }
    
    public Optional<Enrollment> updateProgress(Long courseId, Long userId, Double progress) {
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findByCourseIdAndUserId(courseId, userId);
        if (enrollmentOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Enrollment enrollment = enrollmentOpt.get();
        enrollment.setProgress(Math.min(100.0, Math.max(0.0, progress))); // Clamp between 0-100
        return Optional.of(enrollmentRepository.save(enrollment));
    }
}