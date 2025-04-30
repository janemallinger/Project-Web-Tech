<template>
  <div class="home">
    <h1>Welcome to Frog Crew!</h1>
    
    <div class="button-grouping">
      <button @click="viewProfiles">View Member Profiles</button>
      <button @click="viewSchedule">View Game Schedule</button>
      <button v-if="isAdmin" @click="inviteCrew">Invite Crew Member</button>
    </div>
  
  </div>
</template>

<script>
export default {
  methods: {
    viewProfiles() {
      this.$router.push({ name: 'crewProfileList' });
    },
    viewSchedule() {
      this.$router.push({ name: 'gameSchedule' }); 
    },
    inviteCrew() {
      this.$router.push({ name: 'inviteCrew' })
    }
  },
  computed: {
    isAdmin() {
      return localStorage.getItem('userRole') === 'ADMIN';
    }
  }
};
</script>

<style scoped>
.home {
  font-family: 'Arial', sans-serif;
  text-align: center;
  padding: 50px;
  color: purple;
}

.button-grouping {
  display: flex;
  justify-content: center;
  gap: 2rem;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  /* flex-direction: row;
  align-items: center; */
  /* gap: 16px; */
  /* margin-top: 30px; */
}

button {
  padding: 12px 24px;
  font-size: 16px;
  background-color: purple;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);

  &:hover {
  background-color: #7700cc;
  }
}

</style>