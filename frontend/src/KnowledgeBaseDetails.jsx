import { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import { getKnowledgeBase } from './knowledgeBaseService'

function KnowledgeBaseDetails() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [knowledgeBase, setKnowledgeBase] = useState(null)
  const [error, setError] = useState('')

  useEffect(() => {
    getKnowledgeBase(id).then(setKnowledgeBase).catch((requestError) => {
      setError(requestError.response?.data?.message || 'Knowledge base not found.')
    })
  }, [id])

  if (error) return <main className="details-page"><p className="error">{error}</p><button type="button" onClick={() => navigate('/')}>Back</button></main>
  if (!knowledgeBase) return <main className="details-page"><p className="status">Loading...</p></main>
  return <main className="details-page"><Link to="/">Back to dashboard</Link><h1>{knowledgeBase.name}</h1><p>{knowledgeBase.description || 'No description'}</p>{knowledgeBase.archived && <span className="badge">Archived</span>}</main>
}

export default KnowledgeBaseDetails
