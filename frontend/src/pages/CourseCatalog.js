import React, { useState, useEffect } from 'react';
import { Row, Col, Card, Button, Form, Pagination } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { courseAPI, enrollmentAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';

const CourseCatalog = () => {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [tagFilter, setTagFilter] = useState('');
  const { currentUser } = useAuth();

  useEffect(() => {
    fetchCourses();
  }, [currentPage, tagFilter]);

  const fetchCourses = async () => {
    try {
      const params = { page: currentPage, size: 9 };
      if (tagFilter) params.tag = tagFilter;
      
      const response = await courseAPI.getAllCourses(params);
      setCourses(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error('Error fetching courses:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleEnroll = async (courseId) => {
    try {
      await enrollmentAPI.enrollInCourse(courseId);
      alert('Successfully enrolled in course!');
    } catch (error) {
      alert('Enrollment failed: ' + (error.response?.data || 'Unknown error'));
    }
  };

  if (loading) return <div>Loading courses...</div>;

  return (
    <div>
      <h2>Course Catalog</h2>
      
      <Form.Group className="mb-4">
        <Form.Label>Filter by Tag</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter tag to filter courses"
          value={tagFilter}
          onChange={(e) => setTagFilter(e.target.value)}
        />
      </Form.Group>

      <Row>
        {courses.map(course => (
          <Col md={4} key={course.id} className="mb-4">
            <Card>
              <Card.Body>
                <Card.Title>{course.title}</Card.Title>
                <Card.Text>{course.description}</Card.Text>
                <Card.Text>
                  <small className="text-muted">Tags: {course.tags}</small>
                </Card.Text>
                <div className="d-flex gap-2">
                  <Button as={Link} to={`/course/${course.id}`} variant="primary" size="sm">
                    View Details
                  </Button>
                  {currentUser?.role === 'STUDENT' && (
                    <Button 
                      variant="success" 
                      size="sm"
                      onClick={() => handleEnroll(course.id)}
                    >
                      Enroll
                    </Button>
                  )}
                </div>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>

      {totalPages > 1 && (
        <Pagination className="justify-content-center">
          {[...Array(totalPages)].map((_, index) => (
            <Pagination.Item
              key={index}
              active={index === currentPage}
              onClick={() => setCurrentPage(index)}
            >
              {index + 1}
            </Pagination.Item>
          ))}
        </Pagination>
      )}
    </div>
  );
};

export default CourseCatalog;