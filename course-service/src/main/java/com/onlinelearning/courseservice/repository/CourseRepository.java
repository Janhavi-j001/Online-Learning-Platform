package com.onlinelearning.courseservice.repository;

import com.onlinelearning.courseservice.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    Page<Course> findByIsPublishedTrue(Pageable pageable);
    
    @Query("SELECT c FROM Course c WHERE c.isPublished = true AND c.tags LIKE %:tag%")
    Page<Course> findByIsPublishedTrueAndTagsContaining(@Param("tag") String tag, Pageable pageable);
    
    List<Course> findByInstructorId(Long instructorId);
}