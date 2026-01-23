import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
});

// Add token to requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const authAPI = {
  login: (credentials) => api.post('/users/login', credentials),
  register: (userData) => api.post('/users/register', userData),
};

export const courseAPI = {
  getAllCourses: (params) => api.get('/courses', { params }),
  getCourseById: (id) => api.get(`/courses/${id}`),
  createCourse: (courseData) => api.post('/courses', courseData),
  updateCourse: (id, courseData) => api.put(`/courses/${id}`, courseData),
  publishCourse: (id) => api.post(`/courses/${id}/publish`),
  getInstructorCourses: (instructorId) => api.get(`/courses/instructor/${instructorId}`),
};

export const lessonAPI = {
  getLessonsByCourse: (courseId) => api.get(`/lessons/course/${courseId}`),
  getLessonById: (id) => api.get(`/lessons/${id}`),
  createLesson: (lessonData) => api.post('/lessons', lessonData),
  updateLesson: (id, lessonData) => api.put(`/lessons/${id}`, lessonData),
};

export const quizAPI = {
  getQuizByLesson: (lessonId) => api.get(`/quizzes/lesson/${lessonId}`),
  createQuiz: (quizData) => api.post('/quizzes', quizData),
  submitQuiz: (submissionData) => api.post('/quizzes/submit', submissionData),
};

export const enrollmentAPI = {
  enrollInCourse: (courseId) => api.post('/enrollments', { courseId }),
  getUserEnrollments: (userId) => api.get(`/enrollments/user/${userId}`),
  checkEnrollment: (courseId, userId) => api.get(`/enrollments/check/${courseId}/${userId}`),
  updateProgress: (courseId, progress) => api.put(`/enrollments/progress/${courseId}`, { progress }),
};

export default api;