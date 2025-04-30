<template>
  <div class="schedule">
    <h1>Game Schedule</h1>
    <div class="controls">
      <label>Sort By:</label>
      <select v-model="sortKey">
        <option value="gameDate">Date</option>
        <option value="venue">Venue</option>
        <option value="opponent">Opponent</option>
      </select>

      <div class="submit-wrapper">
        <router-link :to="{ name: 'submitAvailability' }" class="submit-button">
          Submit Availability
        </router-link>

        <router-link v-if="isAdmin" :to="{ name: 'createSchedule' }" class="submit-button">
          Create New Game Schedule
        </router-link>

        <router-link v-if="isAdmin" :to="{ name: 'addGameToSchedule' }" class="submit-button">
          Add Game to Schedule
        </router-link>

        <router-link v-if="isAdmin" :to="{ name: 'scheduleCrew' }" class="submit-button">
          Schedule Crew
        </router-link>
      </div>
    </div>

    <div v-if="error" class="error-message">{{ error }}</div>

    <div v-if="sortedGames.length">
      <div v-for="game in sortedGames" :key="game.gameId" class="game-card">
        <h3>{{ formatDate(game.gameDate) }} - vs {{ game.opponent }}</h3>
        <p><strong>Venue:</strong> {{ game.venue }}</p>
        <p><strong>Status:</strong> {{ game.finalized ? 'Finalized' : 'Not Finalized' }}</p>

        <router-link :to="{
          name: 'crewForGame',
          params: { gameId: game.gameId },
          query: { 
            opponent: game.opponent,
            date: formatDate(game.gameDate),
            venue: game.venue
          }
        }"
        class="view-crew">View Crew Details</router-link>
      </div>
    </div>

    <div v-else>
      <p>No upcoming games</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      sortKey: 'gameDate',
      games: [],
      isAdmin: localStorage.getItem('userRole') === 'ADMIN',
      error: null
    };
  },
  async mounted() {
    try {
      const response = await fetch('http://localhost:8080/api/v1/games');
      const result = await response.json();
      
      if (result.flag) {
        this.games = result.data || [];
      } else {
        this.error = result.message;
      }
    } catch (error) {
      this.error = 'Failed to fetch game schedule';
      console.error('Error:', error);
    }
  },
  methods: {
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      });
    }
  },
  computed: {
    sortedGames() {
      if (!Array.isArray(this.games)) {
        return [];
      }
      return [...this.games].sort((a, b) => {
        const valA = a[this.sortKey];
        const valB = b[this.sortKey];
        
        if (this.sortKey === 'gameDate') {
          return new Date(valA) - new Date(valB);
        }
        
        return valA > valB ? 1 : -1;
      });
    }
  }
};
</script>

<style scoped>
.schedule {
  font-family: 'Arial', sans-serif;
  padding: 30px;
  color: purple;
  text-align: center;
}

.controls {
  margin-bottom: 20px;
}

.game-card {
  background: #f8f8f8;
  margin: 15px auto;
  padding: 20px;
  border-radius: 10px;
  max-width: 500px;
  text-align: left;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.view-crew {
  display: inline-block;
  margin-top: 10px;
  color: white;
  background-color: purple;
  padding: 5px 10px;
  border-radius: 5px;
  text-decoration: none;
}

.view-crew:hover {
  background-color: #7700cc;
}

.submit-wrapper {
  margin-top: 15px;
  display: flex;
  gap: 15px;
  justify-content: center;
}

.submit-button {
  color: white;
  background-color: purple;
  padding: 5px 10px;
  border-radius: 5px;
  text-decoration: none;
  
}

.submit-button:hover {
  background-color: #7700cc;
}

.error-message {
  color: red;
  margin: 10px 0;
}
</style>