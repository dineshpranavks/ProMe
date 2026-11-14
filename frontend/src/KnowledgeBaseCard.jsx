import { Link } from 'react-router-dom'

function KnowledgeBaseCard({ knowledgeBase, onEdit, onDelete, onArchive }) {
  return (
    <article className={`knowledge-card ${knowledgeBase.archived ? 'archived' : ''}`}>
      <div>
        <h2>{knowledgeBase.name}</h2>
        <p>{knowledgeBase.description || 'No description'}</p>
        {knowledgeBase.archived && <span className="badge">Archived</span>}
      </div>
      <div className="card-actions">
        <Link to={`/knowledge-bases/${knowledgeBase.id}`}>Open</Link>
        <button type="button" onClick={() => onEdit(knowledgeBase)}>Edit</button>
        {!knowledgeBase.archived && (
          <button type="button" onClick={() => onArchive(knowledgeBase.id)}>Archive</button>
        )}
        <button type="button" className="danger" onClick={() => onDelete(knowledgeBase)}>Delete</button>
      </div>
    </article>
  )
}

export default KnowledgeBaseCard
