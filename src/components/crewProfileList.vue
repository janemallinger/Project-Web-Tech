<template>
  <div>
    <div class="sorts-and-filters">
      <label>
        Sort By:
        <select v-model="sortKey">
          <option value="firstName">Name</option>
          <option value="role">Role</option>
        </select>
      </label>

      <label>
        Filter By:
        <select v-model="filterRole">
          <option value="all">All</option>
          <option value="ADMIN">Admin</option>
          <option value="CREW_MEMBER">Crew Member</option>
        </select>
      </label>
    </div>

    <div class="crew-list">
      <div v-for="profile in sortedProfiles" :key="profile.userId" class="crew-card">
        <h2>{{ profile.fullName }}</h2>
        <p>Email: {{ profile.email }}</p>
        <p>Phone: {{ profile.phoneNumber }}</p>
        <router-link :to="{
          name: 'crewProfile',
          params: { id: profile.userId }
        }"
        class="view-profile">View Profile</router-link>
        <button v-if="isAdmin" @click="confirmDelete(profile)" class="delete-button">Delete Crew Member</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      crewProfiles: [],
      isAdmin: localStorage.getItem('userRole') === 'ADMIN',
      sortKey: 'firstName',
      filterRole: 'all',
      error: null
    };
  },
  async created() {
    try {
      const response = await fetch('http://localhost:8080/api/v1/crewMember');
      const result = await response.json();
      
      if (result.flag) {
        this.crewProfiles = result.data;
        
      } else {
        this.error = result.message;
      }
    } catch (error) {
      this.error = 'Failed to fetch crew profiles';
      console.error('Error:', error);
    }
  },
  methods: {
    async confirmDelete(profile) {
      const confirmed = window.confirm(
        "Do you want to delete this user?"
      );
      if (confirmed) {
        await this.deleteCrewMember(profile.userId);
      }
    },
    async deleteCrewMember(id) {
      try {
        const response = await fetch(`http://localhost:8080/api/v1/crewMember/${id}`, {
          method: 'DELETE'
        });
        const result = await response.json();
        
        if (result.flag) {
          this.crewProfiles = this.crewProfiles.filter(profile => profile.userId !== id);
        } else {
          alert(result.message);
        }
      } catch (error) {
        console.error('Error deleting crew member:', error);
        alert('Failed to delete crew member');
      }
    }
  },
  computed: {
    sortedProfiles() {
      let filtered = this.crewProfiles;

      if (this.filterRole !== 'all') {
        filtered = filtered.filter(profile => profile.role === this.filterRole);
      }

      return filtered.sort((a, b) => {
        if (a[this.sortKey] < b[this.sortKey]) return -1;
        if (a[this.sortKey] > b[this.sortKey]) return 1;
        return 0;
      });
    }
  }
};
</script>

<style scoped>
.crew-list {
  text-align: center;
  font-family: 'Arial', sans-serif;
  padding: 50px;
  display: flex;
  justify-content: center;
  gap: 20px;
  flex-wrap: wrap;
  margin-top: 30px;
}

.crew-card {
  text-align: center;
  background-color: #f8f8f8;
  border: 1px solid #ddd;
  border-radius: 10px;
  width: 200px;
  padding: 20px;
  color: purple;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.view-profile {
  display: inline-block;
  margin-top: 10px;
  color: white;
  background-color: purple;
  padding: 5px 10px;
  border-radius: 5px;
  text-decoration: none;
}

.view-profile:hover {
  background-color: #7700cc;
}

.delete-button {
  margin-top: 10px;
  background-color: purple;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 5px;
  cursor: pointer;
}

.delete-button:hover {
  background-color: #7700cc;
}

.sorts-and-filters {
  color: #7700cc;
  text-align: center;
  margin-top: 20px;
  margin-bottom: 20px;
}

.sorts-and-filters label {
  color: #7700cc;
  margin: 0 10px;
  font-family: 'Arial', sans-serif;
}

select {
  color: #7700cc;
  margin-left: 5px;
  padding: 5px;
}
</style>