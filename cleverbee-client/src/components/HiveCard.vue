<script setup>
import { computed } from 'vue'

const props = defineProps({ hive: { type: Object, required: true } })
defineEmits(['edit', 'delete'])

const statusMap = {
  ACTIVE: { label: 'Активний', cls: 'bg-success' },
  QUARANTINE: { label: 'Карантин', cls: 'bg-danger' },
  WINTERING: { label: 'Зимівля', cls: 'bg-info text-dark' },
  EMPTY: { label: 'Порожній', cls: 'bg-secondary' }
}
const status = computed(() => statusMap[props.hive.status] || { label: props.hive.status, cls: 'bg-secondary' })
</script>

<template>
  <div class="card h-100 shadow-sm">
    <div class="card-body">
      <div class="d-flex justify-content-between align-items-start mb-2">
        <h5 class="card-title mb-0">{{ hive.serialNumber }}</h5>
        <span class="badge" :class="status.cls">{{ status.label }}</span>
      </div>
      <h6 class="card-subtitle text-muted mb-3">Пасіка: {{ hive.apiaryName }}</h6>
      <ul class="list-unstyled small mb-2">
        <li><strong>Тип:</strong> {{ hive.type }}</li>
        <li><strong>Рік матки:</strong> {{ hive.queenYear ?? '—' }}</li>
        <li><strong>Датчиків:</strong> {{ hive.sensorIds?.length || 0 }}</li>
      </ul>
      <p v-if="hive.notes" class="card-text small text-muted">{{ hive.notes }}</p>
    </div>
    <div class="card-footer bg-white d-flex gap-2">
      <button class="btn btn-sm btn-outline-primary" @click="$emit('edit', hive)">Редагувати</button>
      <button class="btn btn-sm btn-outline-danger" @click="$emit('delete', hive)">Видалити</button>
    </div>
  </div>
</template>