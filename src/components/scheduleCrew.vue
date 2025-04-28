<template>
    <div class="schedule-crew">
        <h1>Schedule Crew</h1>

        <div v-if="!selectedGame">
            <h2>Select a Game: </h2>
            <ul>
                <li v-for="game in games" :key="game.id">
                    <button @click="selectGame(game)">
                        {{ game.date }} - {{ game.team }} at {{ game.location }}
                    </button>
                </li>
            </ul>
        </div>

        <div v-else>
            <h2>Assign Crew for {{ selectedGame.date }} - {{ selectedGame.team }}</h2>

            <form @submit.prevent="submitAssignments">
                <div v-for="position in selectedGame.crewPositions" :key="position" class="assignment">
                    <label>{{ position }}: </label>
                    <select v-model="assignments[position]" required>
                        <option disabled value="">Select Crew Member</option>
                        <option v-for="crew in availableCrew" :key="crew.id" :value="crew.name">
                            {{ crew.name }} ({{ crew.position }})
                        </option>
                    </select>
                </div>

                <button type="submit" class="submit-button">Save Assignment</button>
                <button @click="cancelSelection" class="cancel-button">Cancel</button>
            </form>

            <div v-if="saved" class="success-message">
                Crew assigned successfully
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            games: [
                {
                    id: 1,
                    date: '05/01/2025',
                    time: '10:00 AM',
                    team: 'Team B',
                    location: 'Stadium A',
                    crewPositions: ['Camera', 'Talent']
                },
                {
                    id: 2,
                    date: '05/03/2025',
                    time: '02:00 PM',
                    team: 'Team A',
                    location: 'Stadium B',
                    crewPositions: ['Camera', 'Talent']
                },
                {
                    id: 3,
                    date: '05/05/2025',
                    time: '01:30 PM',
                    team: 'Team C',
                    location: 'Stadium C',
                    crewPositions: ['Camera', 'Talent']
                }
            ],
            availableCrew: [
                { id: 1, name: 'Alex Johnson', position: 'Producer' },
                { id: 2, name: 'Brittany Smith', position: 'Camera' },
                { id: 3, name: 'Carlos Diaz', position: 'Talent' },
                { id: 4, name: 'Morgan Lee', position: 'Talent' },
            ],
            selectedGame: null,
            assignments: {},
            saved: false
        }
    },
    methods: {
        selectGame(game) {
            this.selectedGame = game
            this.assignments = {}
            this.saved = false
        },
        cancelSelection() {
            this.selectedGame = null
            this.assignments = {}
        },
        submitAssignments() {
            for (const position of this.selectedGame.crewPositions) {
                if(!this.assignments[position]) {
                    alert(`Must assign a crew memeber for ${position}`)
                    return
                }
            }

            console.log('Crew assigned for game:', this.selectedGame)
            console.log('Assignments:', this.assignments)

            this.saved = true

            setTimeout(() => {
                this.selectedGame = null
                this.assignments = {}
                this.saved = false
            }, 2000)
        }
    }
}

</script>

<style scoped>

.schedule-crew {
    text-align: center;
    padding: 30px;
    max-width: 600px;
    margin: 0 auto;
    color: purple;
    font-family: 'Arial', sans-serif;
}

h2 {
    text-align: center;
    color: purple;
}

ul {
    list-style: none; 
    padding: 0; 
}

li {
    margin-bottom: 10px;

    &:button {
        display: block;
        margin: 0 auto;
    }
}

.assignment {
    margin-bottom: 20px;
}

select {
    width: 100px;
    padding: 8px;
    margin-top: 5px;
    border-radius: 6px;
    border: 1px solid #ccc;
}

button {
    margin-top: 15px;
    padding: 10px 20px;
    background-color: purple;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;

    &:hover {
        background-color: #7700cc;
    }

    &.cancel-button {
        background-color: purple;
        margin-left: 10px;

        &:hover {
        background-color: #7700cc;
        }
    }
}

.success-message {
    margin-top: 20px;
    padding: 10px;
    background-color: lightgreen;
    border: 1px solid #70db70;
    border-radius: 5px;
    color: #207250;
    text-align: center;
}
</style>