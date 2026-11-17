import axios from 'axios'

const http = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8085/api',
  timeout: 5000,
  headers: { 'Content-Type': 'application/json' }
})

export function errorMessage(err) {
  if (!err.response) {
    return 'Немає зв\'язку з сервером.'
  }
  const data = err.response.data
  if (data && data.fields) {
    return Object.entries(data.fields).map(([f, m]) => `${f}: ${m}`).join('; ')
  }
  if (data && data.error) return data.error
  return `Помилка сервера (${err.response.status})`
}

export default http