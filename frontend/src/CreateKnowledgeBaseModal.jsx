import { useState } from 'react'

function CreateKnowledgeBaseModal({ onClose, onSubmit, error }) {
  const [name, setName] = useState('')
  const [description, setDescription] = useState('')

  function submit(event) {
    event.preventDefault()
    onSubmit({ name, description })
  }

  return (
    <div className="modal-backdrop">
      <form className="modal" onSubmit={submit}>
        <h2>Create knowledge base</h2>
        <label>Name<input value={name} onChange={(event) => setName(event.target.value)} maxLength="100" required /></label>
        <label>Description<textarea value={description} onChange={(event) => setDescription(event.target.value)} maxLength="500" /></label>
        {error && <p className="error">{error}</p>}
        <div className="modal-actions"><button type="button" onClick={onClose}>Cancel</button><button type="submit">Create</button></div>
      </form>
    </div>
  )
}

export default CreateKnowledgeBaseModal
