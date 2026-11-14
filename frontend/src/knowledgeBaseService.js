import api from './api'

export async function listKnowledgeBases({ page = 0, size = 10, search = '' } = {}) {
  const response = await api.get('/api/knowledge-bases', {
    params: { page, size, ...(search.trim() ? { search: search.trim() } : {}) },
  })
  return response.data
}

export async function getKnowledgeBase(id) {
  const response = await api.get(`/api/knowledge-bases/${id}`)
  return response.data
}

export async function createKnowledgeBase(payload) {
  const response = await api.post('/api/knowledge-bases', payload)
  return response.data
}

export async function updateKnowledgeBase(id, payload) {
  const response = await api.put(`/api/knowledge-bases/${id}`, payload)
  return response.data
}

export async function deleteKnowledgeBase(id) {
  await api.delete(`/api/knowledge-bases/${id}`)
}

export async function archiveKnowledgeBase(id) {
  const response = await api.patch(`/api/knowledge-bases/${id}/archive`)
  return response.data
}
