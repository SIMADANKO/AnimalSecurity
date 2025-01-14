import { createRouter, createWebHistory } from 'vue-router';
import Insurance from './components/Insurance.vue'; 
import Login from './components/Login.vue'; 

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
  },
  {
    path: '/mypage',
    name: 'Insurance',
    component: Insurance,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;