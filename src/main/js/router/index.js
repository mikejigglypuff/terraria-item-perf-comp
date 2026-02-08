import { createRouter, createWebHistory } from 'vue-router';
import ItemComparison from '../components/ItemComparison.vue';
import ErrorPage from '../components/ErrorPage.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: ItemComparison,
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: ErrorPage,
    props: {
      errorCode: 404,
      errorMessage: '페이지를 찾을 수 없습니다',
      errorDescription: '요청하신 페이지가 존재하지 않거나 이동되었을 수 있습니다.',
    },
  },
];

const router = createRouter({
  history: createWebHistory('/'),
  routes,
});

export default router;
