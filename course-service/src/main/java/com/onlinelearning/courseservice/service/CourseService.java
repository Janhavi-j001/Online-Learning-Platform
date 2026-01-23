package com.onlinelearning.courseservice.service;

import com.onlinelearning.courseservice.entity.Course;
import com.onlinelearning.courseservice.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    public Page<Course> getAllPublishedCourses(String tag, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (tag != null && !tag.isEmpty()) {
            return courseRepository.findByIsPublishedTrueAndTagsContaining(tag, pageable);
        }
        return courseRepository.findByIsPublishedTrue(pageable);
    }
    
    public Course createCourse(Long instructorId, String title, String description, String tags) {
        Course course = new Course(instructorId, title, description, tags);
        return courseRepository.save(course);
    }
    
    public Optional<Course> updateCourse(Long courseId, Long instructorId, String title, String description, String tags) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Course course = courseOpt.get();
        if (!course.getInstructorId().equals(instructorId)) {
            throw new RuntimeException("Unauthorized to update this course");
        }
        
        course.setTitle(title);
        course.setDescription(description);
        course.setTags(tags);
        return Optional.of(courseRepository.save(course));
    }
    
    public boolean publishCourse(Long courseId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            return false;
        }
        
        Course course = courseOpt.get();
        course.setIsPublished(true);
        courseRepository.save(course);
        return true;
    }
    
    public List<Course> getCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }
    
    public Optional<Course> getCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }
}