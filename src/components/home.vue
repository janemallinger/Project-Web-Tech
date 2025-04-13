<template>
  <div class="home">
    <h1>Welcome to Frog Crew!</h1>
    
    <div class="button-grouping">
      <button @click="viewProfiles">View Member Profiles</button>
      <button @click="viewSchedule">View Game Schedule</button>
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
      this.$router.push({ name: 'gameSchedule' }); // make sure this name matches your router config
    },
  },
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
  flex-direction: column;
  align-items: center;
  gap: 16px; /* space between buttons */
  margin-top: 30px;
}

button {
  padding: 12px 24px;
  font-size: 16px;
  background-color: purple;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: darkviolet;
}
</style>