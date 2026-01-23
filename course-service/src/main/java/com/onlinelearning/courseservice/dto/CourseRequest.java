package com.onlinelearning.courseservice.dto;

public class CourseRequest {
    private String title;
    private String description;
    private String tags;
    
    public CourseRequest() {}
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
}