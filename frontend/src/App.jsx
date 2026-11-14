import { useEffect, useState } from 'react'
import { initialiseCsrf, loadCurrentUser, logout } from './api'
import './App.css'

const apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:8080'

function LoginPage() {
  return (
    <main className="auth-page">
      <section className="auth-card">
        <h1>Welcome to ProMe</h1>
        <p>Sign in securely with your existing account.</p>
        <div className="login-actions">
          <a href={`${apiUrl}/oauth2/authorization/google`}>Continue with Google</a>
          <a href={`${apiUrl}/oauth2/authorization/github`}>Continue with GitHub</a>
        </div>
      </section>
    </main>
  )
}

function Profile({ user, onLogout }) {
  return (
    <main className="profile-page">
      <section className="profile-card">
        {user.profileImageUrl && <img src={user.profileImageUrl} alt="" className="avatar" />}
        <h1>{user.displayName}</h1>
        <p>{user.email || 'Email not provided by provider'}</p>
        <div className="profile-actions">
          <a href={`${apiUrl}/api/auth/link/google`}>Link Google account</a>
          <a href={`${apiUrl}/api/auth/link/github`}>Link GitHub account</a>
          <button type="button" onClick={onLogout}>Log out</button>
        </div>
      </section>
    </main>
  )
}

function App() {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    initialiseCsrf()
      .then(() => loadCurrentUser())
      .then(setUser)
      .catch(() => setUser(null))
      .finally(() => setLoading(false))
  }, [])

  async function handleLogout() {
    await logout()
    setUser(null)
  }

  if (loading) return <main className="auth-page"><p>Loading...</p></main>
  return user ? <Profile user={user} onLogout={handleLogout} /> : <LoginPage />
}

export default App
