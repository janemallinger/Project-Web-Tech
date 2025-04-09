<template>
    <div>
      <div class="crew-list">
          <div v-for="profile in crewProfiles" :key="profile.id" class="crew-card">
          <h2>{{ profile.name }}</h2>
          <p>{{ profile.role }}</p>
          <router-link :to="{
              name: 'crewProfile',
              params: { id: profile.id },
              query: { name: profile.name, role: profile.role, availableTimes: profile.availableTimes }
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
    crewProfiles: [
      { id: 1, name: 'John Doe', role: 'Camera Operator', availableTimes: 'Mon-Fri 8am-8pm' },
      { id: 2, name: 'Jane Smith', role: 'Director', availableTimes: 'Tues-Thurs 10am-5pm' },
      { id: 3, name: 'Sarah Johnson', role: 'Talent', availableTimes: 'Sat-Sun 11am-2pm' },
    ],
  };
},
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