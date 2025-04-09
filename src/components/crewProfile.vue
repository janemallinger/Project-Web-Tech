<template>
    <div class="individual-profile">
    <h1>{{ name }}</h1>
    <p><strong>ID:</strong> {{ id }}</p>
    <p><strong>Occupation:</strong> {{ role }}</p>
    <p><strong>Available Times:</strong> {{ availableTimes }}</p>
    </div>
</template>

<script>
export default {
  computed: {
    id () {
      return this.$route.params.id || 'Unknown';
    },
    name() {
      return this.$route.query.name || 'Unknown';
    },
    role() {
      return this.$route.query.role || 'Unknown';
    },
    availableTimes() {
      return this.$route.query.availableTimes || 'Unknown';
    },
  },
};
</script>

<style scoped>
.individual-profile {
  font-family: 'Arial', sans-serif;
  background-color: #f8f8f8;
  padding: 50px;
  color: purple;
  max-width: 600px;
  margin: 0 auto;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  text-align: center;
}

h1 {
  margin-bottom: 20px;
}

p {
  margin: 10px 0;
  font-size: 16px;
}
</style>
