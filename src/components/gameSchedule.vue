<template>
    <div class="schedule">
        <h1>Game Schedule</h1>
    <div class="controls">
      <label>Sort By:</label>
      <select v-model="sortKey">
        <option value="date">Date</option>
        <option value="opponent">Opponent</option>
        <option value="venue">Venue</option>
      </select>
    </div>

    <div v-if="sortedGames.length">
      <div v-for="game in sortedGames" :key="game.id" class="game-card">
        <h3>{{ game.date }} - {{ game.opponent }}</h3>
        <p><strong>Time:</strong> {{ game.time }}</p>
        <p><strong>Venue:</strong> {{ game.venue }}</p>
        <p><strong>Crew Positions:</strong> {{ game.crewPositions.join(', ') }}</p>
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
    fetch('https://localhost:8080/frog-crew-web-tech/api/v1/gameSchedule/games')
      .then(response => response.json()).then(data => {
        if (data.flag) {
          this.games = data.data.map(game => ({
            id: game.gameId,
            date: game.gameDate,
            time: game.gameTime,
            opponent: game.gameName,
            venue: game.gameLocation,
            crewPositions: ['Camera', 'Talent'] 
          }));
        } else {
          console.error('Failed to load games:', data.message);
        }
      }).catch(error => {
        console.error('Fetch error:', error);
      });
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
</style>