<script setup>
import { ref, onMounted } from 'vue'
import http, { errorMessage } from '../api/http'

const apiaries = ref([])
const hivesCount = ref(0)
const sensorsCount = ref(0)
const error = ref('')

onMounted(() => {
  Promise.all([http.get('/apiaries'), http.get('/hives'), http.get('/sensors')])
    .then(([a, h, s]) => {
      apiaries.value = a.data
      hivesCount.value = h.data.length
      sensorsCount.value = s.data.length
    })
    .catch((err) => (error.value = errorMessage(err)))
})
</script>

<template>
  <div>
    <div class="p-4 mb-4 bg-light rounded-3">
      <h1 class="display-6">CleverBee 🐝</h1>
      <p class="lead mb-0">Моніторинг бджолиних сімей за допомогою IoT-датчиків.</p>
    </div>

    <div v-if="error" class="alert alert-danger">{{ error }}</div>

    <div class="row g-3 mb-4">
      <div class="col-12 col-md-4">
        <div class="card text-center"><div class="card-body">
          <h2>{{ apiaries.length }}</h2><div class="text-muted">Пасік</div>
        </div></div>
      </div>
      <div class="col-12 col-md-4">
        <div class="card text-center"><div class="card-body">
          <h2>{{ hivesCount }}</h2><div class="text-muted">Вуликів</div>
        </div></div>
      </div>
      <div class="col-12 col-md-4">
        <div class="card text-center"><div class="card-body">
          <h2>{{ sensorsCount }}</h2><div class="text-muted">Датчиків</div>
        </div></div>
      </div>
    </div>

    <h4>Пасіки</h4>
    <div class="table-responsive">
      <table class="table table-striped align-middle">
        <thead>
          <tr><th>Код</th><th>Назва</th><th>Розташування</th><th>Опис</th></tr>
        </thead>
        <tbody>
          <tr v-for="a in apiaries" :key="a.id">
            <td>{{ a.code }}</td>
            <td>{{ a.name }}</td>
            <td>{{ a.location }}</td>
            <td>{{ a.description }}</td>
          </tr>
          <tr v-if="!apiaries.length">
            <td colspan="4" class="text-center text-muted">Немає даних</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>