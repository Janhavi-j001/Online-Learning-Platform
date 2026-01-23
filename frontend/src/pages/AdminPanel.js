import React, { useState, useEffect } from 'react';
import { Table, Button, Badge } from 'react-bootstrap';
import { courseAPI } from '../services/api';

const AdminPanel = () => {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAllCourses();
  }, []);

  const fetchAllCourses = async () => {
    try {
      const response = await courseAPI.getAllCourses({ page: 0, size: 100 });
      setCourses(response.data.content);
    } catch (error) {
      console.error('Error fetching courses:', error);
    } finally {
      setLoading(false);
    }
  };

  const handlePublishCourse = async (courseId) => {
    try {
      await courseAPI.publishCourse(courseId);
      fetchAllCourses();
      alert('Course published successfully!');
    } catch (error) {
      alert('Error publishing course');
    }
  };

  if (loading) return <div>Loading...</div>;

  return (
    <div>
      <h2>Admin Panel</h2>
      <p>Manage all courses and user activities</p>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Instructor ID</th>
            <th>Description</th>
            <th>Tags</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {courses.map(course => (
            <tr key={course.id}>
              <td>{course.id}</td>
              <td>{course.title}</td>
              <td>{course.instructorId}</td>
              <td>{course.description?.substring(0, 100)}...</td>
              <td>{course.tags}</td>
              <td>
                <Badge bg={course.isPublished ? 'success' : 'warning'}>
                  {course.isPublished ? 'Published' : 'Draft'}
                </Badge>
              </td>
              <td>
                {!course.isPublished && (
                  <Button 
                    variant="success" 
                    size="sm"
                    onClick={() => handlePublishCourse(course.id)}
                  >
                    Publish
                  </Button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default AdminPanel;