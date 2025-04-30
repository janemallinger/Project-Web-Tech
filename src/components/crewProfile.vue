<template>
  <div class="individual-profile">
    <h1>{{ crewMember?.firstName }} {{ crewMember?.lastName }}</h1>
    <p><strong>ID:</strong> {{ crewMember?.userId }}</p>
    <p><strong>Email:</strong> {{ crewMember?.email }}</p>
    <p><strong>Number:</strong> {{ crewMember?.phoneNumber }}</p>
    <p><strong>Role:</strong> {{ crewMember?.role }}</p>
    <p><strong>Positions:</strong> {{ formattedPositions }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      crewMember: null,
      error: null
    }
  },
  computed: {
    userId() {
      return this.$route.params.id;
    },
    formattedPositions() {
      if (!this.crewMember?.positions) return '';
      return Array.isArray(this.crewMember.positions) 
        ? this.crewMember.positions.join(', ')
        : this.crewMember.positions;
    }
  },
  async created() {
    try {
      const response = await fetch(`http://localhost:8080/api/v1/crewMember/${this.userId}`);
      const result = await response.json();
      
      if (result.flag) {
        this.crewMember = result.data;
      } else {
        this.error = result.message;
      }
    } catch (error) {
      this.error = 'Failed to fetch crew member data';
      console.error('Error fetching crew member:', error);
    }
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