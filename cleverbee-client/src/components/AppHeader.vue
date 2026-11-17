<template>
  <nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="container">
      <router-link class="navbar-brand" to="/">CleverBee 🐝</router-link>
      <button class="navbar-toggler" type="button"
              data-bs-toggle="collapse" data-bs-target="#mainNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="mainNav">
        <ul class="navbar-nav ms-auto">
          <li class="nav-item">
            <router-link class="nav-link" active-class="active" to="/" exact>Головна</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" active-class="active" to="/hives">Вулики</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" active-class="active" to="/about">Про проєкт</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>