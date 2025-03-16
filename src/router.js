import { createRouter, createWebHistory } from 'vue-router';
import registration from './components/registration.vue';
import confirmation from './components/confirmation.vue';
import success from './components/success.vue';

const routes = [
    { path: '/', component: registration, name: 'registration' },
  { path: '/confirmation', component: confirmation, name: 'confirmation' }, 
  { path: '/success', component: success, name: 'success' },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
