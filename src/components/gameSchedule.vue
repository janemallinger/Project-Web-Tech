<template>
    <div class="schedule">
        <h1>Game Schedule</h1>
    <div class="controls">
      <label>Sort By:</label>
      <select v-model="sortKey">
        <option value="date">Date</option>
        <option value="team">Team</option>
        <option value="venue">Venue</option>
      </select>

      <div class="submit-wrapper">
        <router-link :to="{ name: 'submitAvailability' }" class="submit-button">
        Submit Availability
        </router-link>

      </div>
    </div>

    <div v-if="sortedGames.length">
      <div v-for="game in sortedGames" :key="game.id" class="game-card">
        <h3>{{ game.date }} - {{ game.team }}</h3>
        <p><strong>Time:</strong> {{ game.time }}</p>
        <p><strong>Venue:</strong> {{ game.venue }}</p>
        <p><strong>Crew Positions:</strong> {{ game.crewPositions.join(', ') }}</p>

        <router-link :to="{
            name: 'crewForGame',
            params: { gameId: game.id },
            query: { team: game.team, date: game.date, time: game.time, venue: game.venue }
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
      sortKey: 'date',
      games: [],
    };
  },
  computed: {
    sortedGames() {
      return [...this.games].sort((a, b) => {
        const valA = a[this.sortKey].toLowerCase?.() || a[this.sortKey];
        const valB = b[this.sortKey].toLowerCase?.() || b[this.sortKey];
        return valA > valB ? 1 : -1;
      });
    }
  },
  mounted() {
    // Mock game data instead of API call
    this.games = [
      {
        id: 1,
        date: '05/01/2025',
        time: '10:00 AM',
        team: 'Team B',
        venue: 'Stadium A',
        crewPositions: ['Camera', 'Talent']
      },
      {
        id: 2,
        date: '05/03/2025',
        time: '02:00 PM',
        team: 'Team A',
        venue: 'Stadium B',
        crewPositions: ['Camera', 'Talent']
      },
      {
        id: 3,
        date: '05/05/2025',
        time: '01:30 PM',
        team: 'Team C',
        venue: 'Stadium C',
        crewPositions: ['Camera', 'Talent']
      }
    ];
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

  &:hover {
  background-color: #7700cc;
  }
}

.submit-wrapper {
  margin-top: 15px;
}

.submit-button {
  color: white;
  background-color: purple;
  padding: 5px 10px;
  border-radius: 5px;
  text-decoration: none;

  &:hover {
  background-color: #7700cc;
  }
}





</style>