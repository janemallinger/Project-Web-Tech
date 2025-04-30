<template>
  <div>
  <div class="sorts-and-filters">
    <label>
      Sort By:
      <select v-model="sortKey">
        <option value="name">Name</option>
        <option value="job">Job</option>
      </select>
    </label>

    <label>
      Filter By:
      <select v-model="filterStatus">
        <option value="all">All</option>
        <option value="active">Active</option>
        <option value="inactive">Inactive</option>
      </select>
    </label>

    <div class="crew-list">
        <div v-for="profile in sortedProfiles" :key="profile.id" class="crew-card">
        <h2>{{ profile.name }}</h2>
        <p>{{ profile.position }}</p>
        <p>{{ profile.status }}</p>
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
    
  </div>
</template>

<script>
export default {
  data() {
    return {
      crewProfiles: [],
    };
  },
  async created() {
    try {
      const response = await fetch('http://localhost:8080/api/crewProfiles');
      if (!response.ok) {
        throw new Error('Failed to load crew profiles');
      }
      this.crewProfiles = await response.json();
    } catch (error) {
      console.error('Error:', error);
      alert('Error fetching crew profiles.');
    }
  },
// data() {
//   return {
//     crewProfiles: [],
//     isAdmin: localStorage.getItem('userRole') === 'admin',
//     sortKey: 'name',
//     filterStatus: 'all',
//   }
// },
// mounted() {
//   this.crewProfiles = [
//     {
//       id: 1,
//       name: "Alex Johnson",
//       position: "Producer",
//       email: "alexj@gmail.com",
//       phoneNumber: "555-1234",
//       status: "active"
//     },
//     {
//       id: 2,
//       name: "Brittany Smith",
//       position: "Camera",
//       email: "bsmith@gmail.com",
//       phoneNumber: "555-5678",
//       status: "inactive"
//     },
//     {
//       id: 3,
//       name: "Carlos Diaz",
//       position: "Talent",
//       email: "carlosdiaz@gmail.com",
//       phoneNumber: "555-9012",
//       status: "active"
//     }
//   ]
// },
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
},
computed: {
  sortedProfiles() {
    let filtered = this.crewProfiles

    if (this.filterStatus !== 'all') {
      filtered = filtered.filter(profile => profile.status === this.filterStatus)
    }

    return filtered.sort((a, b) => {
      if(a[this.sortKey] < b[this.sortKey]) return -1
      if(a[this.sortKey] > b[this.sortKey]) return 1
      return 0
    })
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

.sorts-filters {
color: #7700cc;
text-align: center;
margin-top: 20px;
margin-bottom: 20px;

&:label {
  color: #7700cc;
  margin: 0 10px;
  font-family: 'Arial', sans-serif;
}
}


select {
color: #7700cc;
margin-left: 5px;
padding: 5px;
}



</style>