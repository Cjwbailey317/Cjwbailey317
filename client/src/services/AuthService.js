import axios from 'axios';

const API_BASE_URL = 'http://localhost:9000';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

export default {
  login(user) {
    return apiClient.post('/login', user)
      .catch(error => {
        console.error('Login error:', error.response?.data || error.message);
        throw error;
      });
  },

  register(user) {
    return apiClient.post('/register', user)
      .catch(error => {
        console.error('Register error:', error.response?.data || error.message);
        throw error;
      });
  }
}
