import { createRouter, createWebHistory } from 'vue-router';
import registration from './components/registration.vue';
import confirmation from './components/confirmation.vue';
import success from './components/success.vue';
import home from './components/home.vue';
import crewProfileList from './components/crewProfileList.vue';
import crewProfile from './components/crewProfile.vue';
import gameSchedule from './components/gameSchedule.vue';

const routes = [
  { path: '/', component: registration, name: 'registration' },
  { path: '/confirmation', component: confirmation, name: 'confirmation' }, 
  { path: '/success', component: success, name: 'success' },
  { path: '/home', component: home, name: 'home'},
  { path: '/crewProfileList', component: crewProfileList, name: 'crewProfileList'},
  { path: '/crew/:id', component: crewProfile, name: 'crewProfile' },
  { path: '/schedule', component: gameSchedule, name: 'gameSchedule' },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
