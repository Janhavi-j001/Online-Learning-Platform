package com.onlinelearning.enrollmentservice.repository;

import com.onlinelearning.enrollmentservice.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUserId(Long userId);
    List<Enrollment> findByCourseId(Long courseId);
    Optional<Enrollment> findByCourseIdAndUserId(Long courseId, Long userId);
    boolean existsByCourseIdAndUserId(Long courseId, Long userId);
}