<template>
  <div class="error-page">
    <div class="error-content">
      <h1 class="error-code">{{ errorCode }}</h1>
      <p class="error-message">{{ errorMessage }}</p>
      <p class="error-description">{{ errorDescription }}</p>
      <button class="btn-main" @click="goToMain">
        메인 페이지로
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ErrorPage',
  props: {
    errorCode: {
      type: [String, Number],
      default: 404,
    },
    errorMessage: {
      type: String,
      default: '페이지를 찾을 수 없습니다',
    },
    errorDescription: {
      type: String,
      default: '요청하신 페이지가 존재하지 않거나 이동되었을 수 있습니다.',
    },
  },
  methods: {
    goToMain() {
      window.location.href = '/';
    },
  },
};
</script>

<style scoped>
.error-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
}

.error-content {
  text-align: center;
  max-width: 400px;
}

.error-code {
  font-size: 6rem;
  font-weight: bold;
  color: #5a5a5a;
  margin: 0 0 1rem;
  line-height: 1;
}

.error-message {
  font-size: 1.5rem;
  color: #e0e0e0;
  margin: 0 0 0.5rem;
}

.error-description {
  color: #a0a0a0;
  margin: 0 0 2rem;
  line-height: 1.5;
}

.btn-main {
  padding: 12px 24px;
  font-size: 1rem;
  font-weight: 500;
  color: white;
  background-color: #6b6b6b;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.btn-main:hover {
  background-color: #7a7a7a;
}

.btn-main:active {
  background-color: #5a5a5a;
}
</style>
