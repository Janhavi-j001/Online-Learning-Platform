import React from 'react';
import { Navbar as BootstrapNavbar, Nav, Container, Button } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Navbar = () => {
  const { currentUser, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <BootstrapNavbar bg="dark" variant="dark" expand="lg">
      <Container>
        <BootstrapNavbar.Brand as={Link} to="/">
          Online Learning Platform
        </BootstrapNavbar.Brand>
        <BootstrapNavbar.Toggle />
        <BootstrapNavbar.Collapse>
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/courses">Courses</Nav.Link>
            {currentUser?.role === 'INSTRUCTOR' && (
              <Nav.Link as={Link} to="/instructor">Dashboard</Nav.Link>
            )}
            {currentUser?.role === 'ADMIN' && (
              <Nav.Link as={Link} to="/admin">Admin Panel</Nav.Link>
            )}
          </Nav>
          <Nav>
            {currentUser ? (
              <>
                <Nav.Link disabled>Welcome, {currentUser.username}</Nav.Link>
                <Button variant="outline-light" onClick={handleLogout}>
                  Logout
                </Button>
              </>
            ) : (
              <>
                <Nav.Link as={Link} to="/login">Login</Nav.Link>
                <Nav.Link as={Link} to="/register">Register</Nav.Link>
              </>
            )}
          </Nav>
        </BootstrapNavbar.Collapse>
      </Container>
    </BootstrapNavbar>
  );
};

export default Navbar;