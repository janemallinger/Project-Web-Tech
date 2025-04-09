<template>
    <div class="home">
        <h1>Welcome to Frog Crew!</h1>
        <button @click="viewProfiles">View Memeber Profiles</button>
    </div>
</template>

<script>
export default {
  methods: {
    viewProfiles() {
      this.$router.push({ name: 'crewProfileList' });
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