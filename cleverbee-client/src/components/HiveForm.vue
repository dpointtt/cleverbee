<script setup>
import { reactive, watch } from 'vue'

const props = defineProps({
  hive: { type: Object, default: null },
  apiaries: { type: Array, default: () => [] },
  sensors: { type: Array, default: () => [] },
  saving: Boolean
})
const emit = defineEmits(['save', 'cancel'])

const types = ['LANGSTROTH', 'DADANT', 'WARRE', 'TOP_BAR']
const statuses = ['ACTIVE', 'QUARANTINE', 'WINTERING', 'EMPTY']

const empty = () => ({
  id: null,
  serialNumber: '',
  type: 'LANGSTROTH',
  status: 'ACTIVE',
  queenYear: new Date().getFullYear(),
  notes: '',
  apiaryId: null,
  sensorIds: []
})

const form = reactive(empty())

watch(
  () => props.hive,
  (h) => {
    Object.assign(form, h ? { ...h, sensorIds: [...(h.sensorIds || [])] } : empty())
  },
  { immediate: true }
)

function submit() {
  emit('save', {
    id: form.id,
    serialNumber: form.serialNumber,
    type: form.type,
    status: form.status,
    queenYear: form.queenYear || null,
    notes: form.notes,
    apiaryId: form.apiaryId,
    sensorIds: form.sensorIds
  })
}
</script>

<template>
  <form class="card card-body mb-4" @submit.prevent="submit">
    <h5 class="mb-3">{{ form.id ? 'Редагування вулика' : 'Новий вулик' }}</h5>
    <div class="row g-3">
      <div class="col-md-4">
        <label class="form-label">Серійний номер</label>
        <input v-model.trim="form.serialNumber" class="form-control" maxlength="32" required />
      </div>
      <div class="col-md-4">
        <label class="form-label">Тип</label>
        <select v-model="form.type" class="form-select" required>
          <option v-for="t in types" :key="t" :value="t">{{ t }}</option>
        </select>
      </div>
      <div class="col-md-4">
        <label class="form-label">Статус</label>
        <select v-model="form.status" class="form-select" required>
          <option v-for="s in statuses" :key="s" :value="s">{{ s }}</option>
        </select>
      </div>
      <div class="col-md-6">
        <label class="form-label">Пасіка</label>
        <select v-model="form.apiaryId" class="form-select" required>
          <option :value="null" disabled>— оберіть —</option>
          <option v-for="a in apiaries" :key="a.id" :value="a.id">{{ a.name }} ({{ a.code }})</option>
        </select>
      </div>
      <div class="col-md-6">
        <label class="form-label">Рік матки</label>
        <input v-model.number="form.queenYear" type="number" class="form-control" min="2000" max="2100" />
      </div>
      <div class="col-12">
        <label class="form-label">Датчики</label>
        <div class="d-flex flex-wrap gap-3">
          <div v-for="s in sensors" :key="s.id" class="form-check">
            <input :id="'sensor-' + s.id" v-model="form.sensorIds" :value="s.id"
                   type="checkbox" class="form-check-input" />
            <label :for="'sensor-' + s.id" class="form-check-label">
              {{ s.deviceCode }} <small class="text-muted">({{ s.type }})</small>
            </label>
          </div>
          <span v-if="!sensors.length" class="text-muted small">Датчиків немає</span>
        </div>
      </div>
      <div class="col-12">
        <label class="form-label">Нотатки</label>
        <textarea v-model="form.notes" class="form-control" rows="2"></textarea>
      </div>
    </div>
    <div class="mt-3 d-flex gap-2">
      <button type="submit" class="btn btn-success" :disabled="saving">
        {{ form.id ? 'Зберегти' : 'Додати' }}
      </button>
      <button type="button" class="btn btn-outline-secondary" @click="$emit('cancel')">Скасувати</button>
    </div>
  </form>
</template>