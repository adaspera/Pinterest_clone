import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/Lab1.Server-1.0-SNAPSHOT/api',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    },
});

axiosInstance.interceptors.response.use(
    (response) => response,
    (error) => {
        console.error('API Error:', error);
        return Promise.reject(error);
    }
);

export default axiosInstance;
