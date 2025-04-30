import { createRouter, createWebHistory } from 'vue-router';
import registration from './components/registration.vue';
import confirmation from './components/confirmation.vue';
import success from './components/success.vue';
import login from './components/login.vue';
import home from './components/home.vue';
import crewProfileList from './components/crewProfileList.vue';
import crewProfile from './components/crewProfile.vue';
import gameSchedule from './components/gameSchedule.vue';
import crewForGame from './components/crewForGame.vue';
import submitAvailability from './components/submitAvailability.vue';
import crewInvite from './components/crewInvite.vue';
import createSchedule from './components/createSchedule.vue';
import addGameToSchedule from './components/addGameToSchedule.vue';
import scheduleCrew from './components/scheduleCrew.vue';

const routes = [
  { path: '/', component: login, name: 'login' },
  { path: '/register', component: registration, name: 'registration' },
  { path: '/confirmation', component: confirmation, name: 'confirmation' }, 
  { path: '/success', component: success, name: 'success' },
  { path: '/home', component: home, name: 'home'},
  { path: '/crewProfileList', component: crewProfileList, name: 'crewProfileList'},
  { path: '/crew/:id', component: crewProfile, name: 'crewProfile' },
  { path: '/schedule', component: gameSchedule, name: 'gameSchedule' },
  { path: '/crewForGame/:gameId', component: crewForGame, name: 'crewForGame'},
  { path: '/submitAvailability', component: submitAvailability, name: 'submitAvailability'},
  { path: '/invite', component: crewInvite, name: 'inviteCrew' },
  { path: '/create-schedule', component: createSchedule, name: 'createSchedule' },
  { path: '/addGameToSchedule', component: addGameToSchedule, name: 'addGameToSchedule'},
  { path: '/scheduleCrew', component: scheduleCrew, name: 'scheduleCrew' },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});


router.beforeEach((to, from, next) => {
  const role = localStorage.getItem('userRole');
  
  // Public routes that don't require authentication
  const publicRoutes = ['login', 'registration', 'confirmation', 'success'];
  
  if (!publicRoutes.includes(to.name) && !role) {
    next({ name: 'login' });
  } else if (to.name === 'inviteCrew' && role !== 'ADMIN') {
    alert('This is an admin feature only.');
    next({ name: 'home' });
  } else {
    next();
  }
});

export default router;