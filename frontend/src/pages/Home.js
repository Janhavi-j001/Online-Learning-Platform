import React from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Home = () => {
  const { currentUser } = useAuth();

  return (
    <Container>
      <Row className="mb-5">
        <Col>
          <div className="text-center">
            <h1 className="display-4">Welcome to Online Learning Platform</h1>
            <p className="lead">Discover, learn, and grow with our comprehensive course catalog</p>
            {!currentUser && (
              <div>
                <Button as={Link} to="/register" variant="primary" size="lg" className="me-3">
                  Get Started
                </Button>
                <Button as={Link} to="/courses" variant="outline-primary" size="lg">
                  Browse Courses
                </Button>
              </div>
            )}
          </div>
        </Col>
      </Row>
      
      <Row>
        <Col md={4}>
          <Card className="mb-4">
            <Card.Body>
              <Card.Title>Learn at Your Pace</Card.Title>
              <Card.Text>
                Access courses anytime, anywhere. Learn at your own speed with our flexible platform.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={4}>
          <Card className="mb-4">
            <Card.Body>
              <Card.Title>Expert Instructors</Card.Title>
              <Card.Text>
                Learn from industry experts and experienced educators who are passionate about teaching.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={4}>
          <Card className="mb-4">
            <Card.Body>
              <Card.Title>Interactive Quizzes</Card.Title>
              <Card.Text>
                Test your knowledge with interactive quizzes and track your progress throughout the course.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Home;