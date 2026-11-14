import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080',
  withCredentials: true,
  xsrfCookieName: 'XSRF-TOKEN',
  xsrfHeaderName: 'X-XSRF-TOKEN',
})

export async function loadCurrentUser() {
  const response = await api.get('/api/auth/me')
  return response.data
}

export async function initialiseCsrf() {
  await api.get('/api/auth/csrf')
}

export async function logout() {
  await api.post('/api/auth/logout')
}

export default api
