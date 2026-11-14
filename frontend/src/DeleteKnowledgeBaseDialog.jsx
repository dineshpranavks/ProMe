function DeleteKnowledgeBaseDialog({ knowledgeBase, onClose, onConfirm, error }) {
  return (
    <div className="modal-backdrop">
      <div className="modal">
        <h2>Delete knowledge base?</h2>
        <p>This will permanently delete <strong>{knowledgeBase.name}</strong>.</p>
        {error && <p className="error">{error}</p>}
        <div className="modal-actions"><button type="button" onClick={onClose}>Cancel</button><button type="button" className="danger" onClick={onConfirm}>Delete</button></div>
      </div>
    </div>
  )
}

export default DeleteKnowledgeBaseDialog
