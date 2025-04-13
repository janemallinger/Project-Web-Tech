<template>
    <div>
      <div class="crew-list">
          <div v-for="profile in crewProfiles" :key="profile.id" class="crew-card">
          <h2>{{ profile.name }}</h2>
          <p>{{ profile.email }}</p>
          <router-link :to="{
              name: 'crewProfile',
              params: { id: profile.id },
              query: { name: profile.name, email: profile.email, phoneNumber: profile.phoneNumber }
              }"
            class="view-profile">View Profile</router-link>
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
mounted() {
  this.fetchCrewProfiles();
},
methods: {
  fetchCrewProfiles() {
    fetch('https://localhost:8080/frog-crew-web-tech/api/v1/crewMembers')
    .then(response => response.json()).then(data => {
      if(data.flag) {
        this.crewProfiles = data.data.map(profile => ({
          id: profile.userId,
          name: profile.fullName,
          email: profile.email,
          phoneNumber: profile.phoneNumber,
        }));
      } else {
        console.error('Failed to load profiles', data.message);
      }
    }).catch(error => {
      console.error('Fetch error:', error);
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
background-color: darkviolet;
}
</style>