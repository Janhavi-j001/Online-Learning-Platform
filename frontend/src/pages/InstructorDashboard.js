import React, { useState, useEffect } from 'react';
import { Card, Button, Modal, Form, Table } from 'react-bootstrap';
import { courseAPI, lessonAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';

const InstructorDashboard = () => {
  const [courses, setCourses] = useState([]);
  const [showCourseModal, setShowCourseModal] = useState(false);
  const [showLessonModal, setShowLessonModal] = useState(false);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [courseForm, setCourseForm] = useState({ title: '', description: '', tags: '' });
  const [lessonForm, setLessonForm] = useState({ title: '', content: '', videoUrl: '', orderIndex: 0 });
  const { currentUser } = useAuth();

  useEffect(() => {
    if (currentUser) {
      fetchInstructorCourses();
    }
  }, [currentUser]);

  const fetchInstructorCourses = async () => {
    try {
      const response = await courseAPI.getInstructorCourses(currentUser.id);
      setCourses(response.data);
    } catch (error) {
      console.error('Error fetching courses:', error);
    }
  };

  const handleCreateCourse = async () => {
    try {
      await courseAPI.createCourse(courseForm);
      setShowCourseModal(false);
      setCourseForm({ title: '', description: '', tags: '' });
      fetchInstructorCourses();
    } catch (error) {
      alert('Error creating course');
    }
  };

  const handleCreateLesson = async () => {
    try {
      await lessonAPI.createLesson({
        ...lessonForm,
        courseId: selectedCourse.id
      });
      setShowLessonModal(false);
      setLessonForm({ title: '', content: '', videoUrl: '', orderIndex: 0 });
    } catch (error) {
      alert('Error creating lesson');
    }
  };

  const handlePublishCourse = async (courseId) => {
    try {
      await courseAPI.publishCourse(courseId);
      fetchInstructorCourses();
      alert('Course published successfully!');
    } catch (error) {
      alert('Error publishing course');
    }
  };

  return (
    <div>
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Instructor Dashboard</h2>
        <Button variant="primary" onClick={() => setShowCourseModal(true)}>
          Create New Course
        </Button>
      </div>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Tags</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {courses.map(course => (
            <tr key={course.id}>
              <td>{course.title}</td>
              <td>{course.description}</td>
              <td>{course.tags}</td>
              <td>{course.isPublished ? 'Published' : 'Draft'}</td>
              <td>
                <Button 
                  variant="info" 
                  size="sm" 
                  className="me-2"
                  onClick={() => {
                    setSelectedCourse(course);
                    setShowLessonModal(true);
                  }}
                >
                  Add Lesson
                </Button>
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

      {/* Create Course Modal */}
      <Modal show={showCourseModal} onHide={() => setShowCourseModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Create New Course</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Title</Form.Label>
              <Form.Control
                type="text"
                value={courseForm.title}
                onChange={(e) => setCourseForm({...courseForm, title: e.target.value})}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Description</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                value={courseForm.description}
                onChange={(e) => setCourseForm({...courseForm, description: e.target.value})}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Tags</Form.Label>
              <Form.Control
                type="text"
                value={courseForm.tags}
                onChange={(e) => setCourseForm({...courseForm, tags: e.target.value})}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowCourseModal(false)}>
            Cancel
          </Button>
          <Button variant="primary" onClick={handleCreateCourse}>
            Create Course
          </Button>
        </Modal.Footer>
      </Modal>

      {/* Create Lesson Modal */}
      <Modal show={showLessonModal} onHide={() => setShowLessonModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Add Lesson to {selectedCourse?.title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Title</Form.Label>
              <Form.Control
                type="text"
                value={lessonForm.title}
                onChange={(e) => setLessonForm({...lessonForm, title: e.target.value})}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Content</Form.Label>
              <Form.Control
                as="textarea"
                rows={5}
                value={lessonForm.content}
                onChange={(e) => setLessonForm({...lessonForm, content: e.target.value})}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Video URL (optional)</Form.Label>
              <Form.Control
                type="url"
                value={lessonForm.videoUrl}
                onChange={(e) => setLessonForm({...lessonForm, videoUrl: e.target.value})}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Order Index</Form.Label>
              <Form.Control
                type="number"
                value={lessonForm.orderIndex}
                onChange={(e) => setLessonForm({...lessonForm, orderIndex: parseInt(e.target.value)})}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowLessonModal(false)}>
            Cancel
          </Button>
          <Button variant="primary" onClick={handleCreateLesson}>
            Add Lesson
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default InstructorDashboard;