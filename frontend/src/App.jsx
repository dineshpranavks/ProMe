import { useEffect, useState } from 'react'
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { initialiseCsrf, loadCurrentUser, logout } from './api'
import KnowledgeBaseDetails from './KnowledgeBaseDetails'
import KnowledgeDashboard from './KnowledgeDashboard'
import './App.css'

const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8080'

function LoginPage() {
  return <main className="auth-page"><section className="auth-card"><h1>Welcome to ProMe</h1><p>Sign in securely with your existing account.</p><div className="login-actions"><a href={`${apiUrl}/oauth2/authorization/google`}>Continue with Google</a><a href={`${apiUrl}/oauth2/authorization/github`}>Continue with GitHub</a></div></section></main>
}

function ProtectedRoutes({ user, onLogout }) {
  return <Routes><Route path="/" element={<KnowledgeDashboard user={user} onLogout={onLogout} />} /><Route path="/knowledge-bases/:id" element={<KnowledgeBaseDetails />} /><Route path="*" element={<Navigate to="/" replace />} /></Routes>
}

function App() {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)
  useEffect(() => {
    initialiseCsrf().then(loadCurrentUser).then(setUser).catch(() => setUser(null)).finally(() => setLoading(false))
  }, [])
  async function handleLogout() { await logout(); setUser(null) }
  return <BrowserRouter>{loading ? <main className="auth-page"><p>Loading...</p></main> : user ? <ProtectedRoutes user={user} onLogout={handleLogout} /> : <LoginPage />}</BrowserRouter>
}

export default App
