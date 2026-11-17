<template>
  <div>
    <h2>Про проєкт</h2>
    <p>
      CleverBee 🐝 — система цифрового моніторингу бджолиних сімей.
      До вуликів підключаються IoT-датчики (вага, температура, вологість, акустика), а бджоляр
      керує пасіками, вуликами та датчиками через веб-інтерфейс.
    </p>
    <div class="row g-3">
      <div class="col-md-4"><div class="card h-100"><div class="card-body">
        <h5>Frontend</h5><p class="mb-0">Vue 3, Vite, vue-router, axios, Bootstrap 5</p>
      </div></div></div>
      <div class="col-md-4"><div class="card h-100"><div class="card-body">
        <h5>Backend</h5><p class="mb-0">Spring Boot 4, Spring Data JPA, REST API (JSON)</p>
      </div></div></div>
      <div class="col-md-4"><div class="card h-100"><div class="card-body">
        <h5>База даних</h5><p class="mb-0">PostgreSQL, схема генерується Hibernate</p>
      </div></div></div>
    </div>
  </div>
</template>