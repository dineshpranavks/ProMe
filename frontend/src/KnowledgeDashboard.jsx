import { useCallback, useEffect, useState } from 'react'
import KnowledgeBaseCard from './KnowledgeBaseCard'
import CreateKnowledgeBaseModal from './CreateKnowledgeBaseModal'
import EditKnowledgeBaseModal from './EditKnowledgeBaseModal'
import DeleteKnowledgeBaseDialog from './DeleteKnowledgeBaseDialog'
import {
  archiveKnowledgeBase,
  createKnowledgeBase,
  deleteKnowledgeBase,
  listKnowledgeBases,
  updateKnowledgeBase,
} from './knowledgeBaseService'

function KnowledgeDashboard({ user, onLogout }) {
  const [page, setPage] = useState(0)
  const [search, setSearch] = useState('')
  const [knowledgeBases, setKnowledgeBases] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [modal, setModal] = useState(null)
  const [modalError, setModalError] = useState('')

  const load = useCallback(async () => {
    setError('')
    try {
      setKnowledgeBases(await listKnowledgeBases({ page, search }))
    } catch (requestError) {
      setError(requestError.response?.data?.message || 'Unable to load knowledge bases.')
    } finally {
      setLoading(false)
    }
  }, [page, search])

  // oxlint-disable-next-line react-hooks/set-state-in-effect
  useEffect(() => { load() }, [load])

  async function runAction(action) {
    setModalError('')
    setLoading(true)
    try {
      await action()
      setModal(null)
      await load()
    } catch (requestError) {
      setModalError(requestError.response?.data?.message || 'Unable to complete that action.')
    }
  }

  return (
    <main className="dashboard">
      <header className="dashboard-header">
        <div><p className="eyebrow">ProMe</p><h1>Knowledge bases</h1><p>{user.displayName}</p></div>
        <button type="button" onClick={onLogout}>Log out</button>
      </header>
      <section className="toolbar">
        <input aria-label="Search knowledge bases" placeholder="Search by name" value={search} onChange={(event) => { setLoading(true); setPage(0); setSearch(event.target.value) }} />
        <button type="button" onClick={() => { setModalError(''); setModal({ type: 'create' }) }}>New knowledge base</button>
      </section>
      {error && <p className="error">{error}</p>}
      {loading && <p className="status">Loading knowledge bases...</p>}
      {!loading && knowledgeBases?.content.length === 0 && <p className="empty">No knowledge bases found.</p>}
      <section className="knowledge-grid">
        {knowledgeBases?.content.map((knowledgeBase) => (
          <KnowledgeBaseCard key={knowledgeBase.id} knowledgeBase={knowledgeBase}
            onEdit={(item) => { setModalError(''); setModal({ type: 'edit', item }) }}
            onDelete={(item) => { setModalError(''); setModal({ type: 'delete', item }) }}
            onArchive={(id) => runAction(() => archiveKnowledgeBase(id))} />
        ))}
      </section>
      {knowledgeBases && knowledgeBases.totalPages > 1 && (
        <nav className="pagination">
          <button type="button" disabled={page === 0} onClick={() => { setLoading(true); setPage(page - 1) }}>Previous</button>
          <span>Page {page + 1} of {knowledgeBases.totalPages}</span>
          <button type="button" disabled={page + 1 >= knowledgeBases.totalPages} onClick={() => { setLoading(true); setPage(page + 1) }}>Next</button>
        </nav>
      )}
      {modal?.type === 'create' && <CreateKnowledgeBaseModal onClose={() => setModal(null)} error={modalError} onSubmit={(payload) => runAction(async () => { await createKnowledgeBase(payload) })} />}
      {modal?.type === 'edit' && <EditKnowledgeBaseModal knowledgeBase={modal.item} onClose={() => setModal(null)} error={modalError} onSubmit={(payload) => runAction(async () => { await updateKnowledgeBase(modal.item.id, payload) })} />}
      {modal?.type === 'delete' && <DeleteKnowledgeBaseDialog knowledgeBase={modal.item} onClose={() => setModal(null)} error={modalError} onConfirm={() => runAction(() => deleteKnowledgeBase(modal.item.id))} />}
    </main>
  )
}

export default KnowledgeDashboard
