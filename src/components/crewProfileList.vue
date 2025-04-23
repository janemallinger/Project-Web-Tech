<template>
    <div>
      <div class="crew-list">
          <div v-for="profile in crewProfiles" :key="profile.id" class="crew-card">
          <h2>{{ profile.name }}</h2>
          <p>{{ profile.position }}</p>
          <router-link :to="{
              name: 'crewProfile',
              params: { id: profile.id },
              query: { name: profile.name, email: profile.email, phoneNumber: profile.phoneNumber }
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
      isAdmin: localStorage.getItem('userRole') === 'admin',
    }
  },
  mounted() {
    this.crewProfiles = [
      {
        id: 1,
        name: "Alex Johnson",
        position: "Producer",
        email: "alexj@gmail.com",
        phoneNumber: "555-1234"
      },
      {
        id: 2,
        name: "Brittany Smith",
        position: "Camera",
        email: "bsmith@gmail.com",
        phoneNumber: "555-5678"
      },
      {
        id: 3,
        name: "Carlos Diaz",
        position: "Talent",
        email: "carlosdiaz@gmail.com",
        phoneNumber: "555-9012"
      }
    ]
  },
  methods: {
    confirmDelete(profile) {
      const confirmed = window.confirm (
        `Do you want to delete ${profile.name}?`
      )
      if (confirmed) {
        this.deleteCrewMember(profile.id)
      }
    },
    deleteCrewMember(id) {
      this.crewProfiles = this.crewProfiles.filter(profile => profile.id !== id)
      alert('Crew member has been deleted')
    }
  }
}
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

  &:hover {
  background-color: #7700cc;
  }
}

.delete-button {
  margin-top: 10px;
  background-color: purple;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 5px;
  cursor: pointer;

  &:hover {
    background-color: #7700cc;
  }
}


</style>