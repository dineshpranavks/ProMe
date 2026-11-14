import { useState } from 'react'

function EditKnowledgeBaseModal({ knowledgeBase, onClose, onSubmit, error }) {
  const [name, setName] = useState(knowledgeBase.name)
  const [description, setDescription] = useState(knowledgeBase.description || '')

  function submit(event) {
    event.preventDefault()
    onSubmit({ name, description })
  }

  return (
    <div className="modal-backdrop">
      <form className="modal" onSubmit={submit}>
        <h2>Edit knowledge base</h2>
        <label>Name<input value={name} onChange={(event) => setName(event.target.value)} maxLength="100" required /></label>
        <label>Description<textarea value={description} onChange={(event) => setDescription(event.target.value)} maxLength="500" /></label>
        {error && <p className="error">{error}</p>}
        <div className="modal-actions"><button type="button" onClick={onClose}>Cancel</button><button type="submit">Save</button></div>
      </form>
    </div>
  )
}

export default EditKnowledgeBaseModal
