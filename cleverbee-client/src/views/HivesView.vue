<script setup>
import { ref, onMounted } from 'vue'
import http, { errorMessage } from '../api/http'
import HiveCard from '../components/HiveCard.vue'
import HiveForm from '../components/HiveForm.vue'

const hives = ref([])
const apiaries = ref([])
const sensors = ref([])
const loading = ref(false)
const saving = ref(false)
const error = ref('')
const success = ref('')
const showForm = ref(false)
const editing = ref(null)

function notify(msg) {
  success.value = msg
  setTimeout(() => (success.value = ''), 3000)
}

function load() {
  loading.value = true
  error.value = ''
  Promise.all([http.get('/hives'), http.get('/apiaries'), http.get('/sensors')])
    .then(([h, a, s]) => {
      hives.value = h.data
      apiaries.value = a.data
      sensors.value = s.data
    })
    .catch((err) => (error.value = errorMessage(err)))
    .finally(() => (loading.value = false))
}

function openCreate() {
  editing.value = null
  showForm.value = true
}

function openEdit(hive) {
  editing.value = hive
  showForm.value = true
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

function closeForm() {
  showForm.value = false
  editing.value = null
}

function save(payload) {
  saving.value = true
  error.value = ''
  const request = payload.id
    ? http.put(`/hives/${payload.id}`, payload)
    : http.post('/hives', payload)
  const wasEdit = !!payload.id

  request
    .then(() => {
      notify(wasEdit ? 'Вулик оновлено' : 'Вулик додано')
      closeForm()
      load()
    })
    .catch((err) => (error.value = errorMessage(err)))
    .finally(() => (saving.value = false))
}

function remove(hive) {
  if (!confirm(`Видалити вулик ${hive.serialNumber}?`)) return
  error.value = ''
  http.delete(`/hives/${hive.id}`)
    .then(() => {
      notify('Вулик видалено')
      load()
    })
    .catch((err) => (error.value = errorMessage(err)))
}

onMounted(load)
</script>

<template>
  <div>
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h2 class="mb-0">Вулики</h2>
      <button class="btn btn-success" @click="openCreate" :disabled="showForm">+ Додати вулик</button>
    </div>

    <div v-if="error" class="alert alert-danger alert-dismissible">
      {{ error }}
      <button type="button" class="btn-close" @click="error = ''"></button>
    </div>
    <div v-if="success" class="alert alert-success">{{ success }}</div>

    <HiveForm v-if="showForm" :hive="editing" :apiaries="apiaries" :sensors="sensors"
              :saving="saving" @save="save" @cancel="closeForm" />

    <div v-if="loading" class="text-center py-5">
      <div class="spinner-border text-warning"></div>
    </div>

    <div v-else-if="hives.length" class="row g-3">
      <div v-for="h in hives" :key="h.id" class="col-12 col-md-6 col-lg-4">
        <HiveCard :hive="h" @edit="openEdit" @delete="remove" />
      </div>
    </div>

    <p v-else-if="!error" class="text-muted">Вуликів поки немає.</p>
    <button v-if="error && !hives.length" class="btn btn-outline-primary" @click="load">Повторити</button>
  </div>
</template>