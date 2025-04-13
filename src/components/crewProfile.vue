<template>
    <div class="individual-profile">
      <h1>{{ name }}</h1>
      <p><strong>ID:</strong> {{ id }}</p>
      <p><strong>Email:</strong> {{ email }}</p>
      <p><strong>Number:</strong> {{ phoneNumber }}</p>
    </div>
</template>

<script>
export default {
  computed: {
    id() {
      return this.$route.params.id;
    },
    name() {
      return this.$route.query.name || 'Unknown';
    },
    email() {
      return this.$route.query.email || 'Unknown';
    },
    phoneNumber() {
      return this.$route.query.phoneNumber || 'Unknown';
    },
  }
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
