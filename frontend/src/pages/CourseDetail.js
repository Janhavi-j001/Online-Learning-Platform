import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Card, ListGroup, Button, Modal, Form } from 'react-bootstrap';
import { courseAPI, lessonAPI, quizAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';

const CourseDetail = () => {
  const { id } = useParams();
  const [course, setCourse] = useState(null);
  const [lessons, setLessons] = useState([]);
  const [selectedLesson, setSelectedLesson] = useState(null);
  const [quiz, setQuiz] = useState(null);
  const [showQuiz, setShowQuiz] = useState(false);
  const [quizAnswers, setQuizAnswers] = useState([]);
  const { currentUser } = useAuth();

  useEffect(() => {
    fetchCourseDetails();
    fetchLessons();
  }, [id]);

  const fetchCourseDetails = async () => {
    try {
      const response = await courseAPI.getCourseById(id);
      setCourse(response.data);
    } catch (error) {
      console.error('Error fetching course:', error);
    }
  };

  const fetchLessons = async () => {
    try {
      const response = await lessonAPI.getLessonsByCourse(id);
      setLessons(response.data);
    } catch (error) {
      console.error('Error fetching lessons:', error);
    }
  };

  const handleLessonClick = async (lesson) => {
    setSelectedLesson(lesson);
    try {
      const response = await quizAPI.getQuizByLesson(lesson.id);
      setQuiz(response.data);
    } catch (error) {
      setQuiz(null);
    }
  };

  const handleQuizSubmit = async () => {
    try {
      const response = await quizAPI.submitQuiz({
        quizId: quiz.id,
        answers: quizAnswers
      });
      alert(`Quiz completed! Score: ${response.data.score}/${response.data.totalQuestions} (${response.data.percentage.toFixed(1)}%)`);
      setShowQuiz(false);
    } catch (error) {
      alert('Error submitting quiz');
    }
  };

  if (!course) return <div>Loading...</div>;

  return (
    <div>
      <Card className="mb-4">
        <Card.Body>
          <Card.Title>{course.title}</Card.Title>
          <Card.Text>{course.description}</Card.Text>
          <Card.Text><strong>Tags:</strong> {course.tags}</Card.Text>
        </Card.Body>
      </Card>

      <div className="row">
        <div className="col-md-4">
          <Card>
            <Card.Header>Lessons</Card.Header>
            <ListGroup variant="flush">
              {lessons.map(lesson => (
                <ListGroup.Item 
                  key={lesson.id}
                  action
                  onClick={() => handleLessonClick(lesson)}
                  active={selectedLesson?.id === lesson.id}
                >
                  {lesson.title}
                </ListGroup.Item>
              ))}
            </ListGroup>
          </Card>
        </div>

        <div className="col-md-8">
          {selectedLesson ? (
            <Card>
              <Card.Header className="d-flex justify-content-between align-items-center">
                <span>{selectedLesson.title}</span>
                {quiz && (
                  <Button variant="primary" onClick={() => setShowQuiz(true)}>
                    Take Quiz
                  </Button>
                )}
              </Card.Header>
              <Card.Body>
                <div dangerouslySetInnerHTML={{ __html: selectedLesson.content }} />
                {selectedLesson.videoUrl && (
                  <div className="mt-3">
                    <video controls width="100%">
                      <source src={selectedLesson.videoUrl} type="video/mp4" />
                    </video>
                  </div>
                )}
              </Card.Body>
            </Card>
          ) : (
            <Card>
              <Card.Body>
                <Card.Text>Select a lesson to view its content</Card.Text>
              </Card.Body>
            </Card>
          )}
        </div>
      </div>

      {/* Quiz Modal */}
      <Modal show={showQuiz} onHide={() => setShowQuiz(false)} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>Quiz: {selectedLesson?.title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {quiz && JSON.parse(quiz.questions).map((question, index) => (
            <div key={index} className="mb-4">
              <h6>{question.question}</h6>
              {question.options.map((option, optIndex) => (
                <Form.Check
                  key={optIndex}
                  type="radio"
                  name={`question-${index}`}
                  label={option}
                  onChange={() => {
                    const newAnswers = [...quizAnswers];
                    newAnswers[index] = optIndex;
                    setQuizAnswers(newAnswers);
                  }}
                />
              ))}
            </div>
          ))}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowQuiz(false)}>
            Cancel
          </Button>
          <Button variant="primary" onClick={handleQuizSubmit}>
            Submit Quiz
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default CourseDetail;