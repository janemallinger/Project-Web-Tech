<template>
    <div class="schedule-crew">
        <h1>Schedule Crew</h1>

        <div v-if="error" class="error-message">{{ error }}</div>

        <div v-if="!selectedGame">
            <h2>Select a Game: </h2>
            <ul>
                <li v-for="game in games" :key="game.gameId">
                    <button @click="selectGame(game)">
                        {{ formatDate(game.gameDate) }} - vs {{ game.opponent }} at {{ game.venue }}
                    </button>
                </li>
            </ul>
        </div>

        <div v-else>
            <h2>Assign Crew for {{ formatDate(selectedGame.gameDate) }} - vs {{ selectedGame.opponent }}</h2>

            <div v-if="error" class="error-message">{{ error }}</div>

            <form @submit.prevent="submitAssignments">
                <div v-for="position in uniquePositions" :key="position" class="assignment">
                    <label>{{ position }}: </label>
                    <select v-model="assignments[position]" required>
                        <option disabled value="">Select Crew Member</option>
                        <option v-for="crew in availableCrew" :key="crew.userId" :value="crew.userId">
                            {{ crew.firstName }} {{ crew.lastName }} ({{ crew.positions?.join(', ') }})
                        </option>
                    </select>
                </div>

                <div v-if="uniquePositions.length === 0" class="no-positions">
                    <p>No positions available for assignment. Please add crew members with positions first.</p>
                </div>

                <div v-if="availableCrew.length === 0" class="no-crew">
                    <p>No crew members available for assignment.</p>
                </div>

                <button type="submit" class="submit-button" :disabled="isSubmitting || uniquePositions.length === 0">Save Assignment</button>
                <button @click="cancelSelection" class="cancel-button" :disabled="isSubmitting">Cancel</button>
            </form>

            <div v-if="success" class="success-message">
                Crew assigned successfully
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            games: [],
            availableCrew: [],
            selectedGame: null,
            assignments: {},
            isSubmitting: false,
            error: null,
            success: false
        }
    },
    computed: {
        gameId() {
            return this.$route.query.gameId;
        },
        uniquePositions() {
            const positions = new Set();
            console.log('Calculating unique positions from crew:', this.availableCrew);
            
            this.availableCrew.forEach(crew => {
                console.log('Processing crew member positions:', crew.positions);
                if (crew.positions && Array.isArray(crew.positions)) {
                    crew.positions.forEach(position => {
                        const trimmedPosition = position.trim();
                        if (trimmedPosition) {
                            positions.add(trimmedPosition);
                        }
                    });
                }
            });
            
            const uniquePositionsArray = Array.from(positions);
            console.log('Final unique positions:', uniquePositionsArray);
            return uniquePositionsArray;
        }
    },
    async created() {
        try {
            const crewResponse = await fetch('http://localhost:8080/api/v1/crewMember');
            const crewResult = await crewResponse.json();
            
            if (crewResult.flag) {
                console.log('Raw crew data:', crewResult.data);
                this.availableCrew = crewResult.data.map(crew => {
                    console.log('Processing crew member:', crew);
                    
                    const nameParts = crew.fullName ? crew.fullName.split(' ') : ['', ''];
                    const firstName = nameParts[0] || '';
                    const lastName = nameParts.slice(1).join(' ') || '';
                    const positions = crew.positions || [];
                    
                    const processedCrew = {
                        userId: crew.userId,
                        firstName: firstName,
                        lastName: lastName,
                        email: crew.email,
                        phoneNumber: crew.phoneNumber,
                        role: crew.role || 'Crew Member',
                        positions: positions
                    };
                    
                    console.log('Processed crew member:', processedCrew);
                    return processedCrew;
                });
                
                console.log('All processed crew members:', this.availableCrew);
            } else {
                this.error = crewResult.message;
            }

            if (this.gameId) {
                const gamesResponse = await fetch('http://localhost:8080/api/v1/games');
                const gamesResult = await gamesResponse.json();
                
                if (gamesResult.flag) {
                    this.games = gamesResult.data;
                    this.selectedGame = this.games.find(game => game.gameId === parseInt(this.gameId));
                    if (!this.selectedGame) {
                        this.error = 'Game not found';
                    }
                } else {
                    this.error = gamesResult.message;
                }
            } else {
                const gamesResponse = await fetch('http://localhost:8080/api/v1/games');
                const gamesResult = await gamesResponse.json();
                
                if (gamesResult.flag) {
                    this.games = gamesResult.data;
                } else {
                    this.error = gamesResult.message;
                }
            }
        } catch (error) {
            this.error = 'Failed to fetch data';
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
        },
        selectGame(game) {
            this.selectedGame = game;
            this.assignments = {};
            this.success = false;
        },
        cancelSelection() {
            this.selectedGame = null;
            this.assignments = {};
        },
        async submitAssignments() {
            this.isSubmitting = true;
            this.error = null;
            this.success = false;

            try {
                for (const position of this.uniquePositions) {
                    if (!this.assignments[position]) {
                        this.error = `Must assign a crew member for ${position}`;
                        this.isSubmitting = false;
                        return;
                    }
                }

                const assignmentData = {
                    gameId: this.selectedGame.gameId,
                    assignments: Object.entries(this.assignments).map(([position, userId]) => ({
                        userId: parseInt(userId),
                        gameId: this.selectedGame.gameId,
                        position: position,
                        reportTime: "1:00:00", 
                        reportLocation: "Gate A", 
                    }))
                };

                console.log('Submitting assignments:', assignmentData); 

                const response = await fetch('http://localhost:8080/api/v1/crewSchedule/' + this.selectedGame.gameId, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(assignmentData.assignments)
                });

                const result = await response.json();
                console.log('Response:', result);

                if (result.flag) {
                    this.success = true;
                    this.assignments = {};
                } else {
                    this.error = result.message || 'Failed to save assignments';
                }
            } catch (error) {
                this.error = 'Failed to save assignments. Please try again.';
                console.error('Error:', error);
            } finally {
                this.isSubmitting = false;
            }
        }
    }
};
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
}

li button {
    display: block;
    margin: 0 auto;
}

.assignment {
    margin-bottom: 20px;
}

select {
    width: 200px;
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
}

button:hover {
    background-color: #7700cc;
}

button.cancel-button {
    background-color: purple;
    margin-left: 10px;
}

button.cancel-button:hover {
    background-color: #7700cc;
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

.error-message {
    margin-top: 20px;
    padding: 10px;
    background-color: #ffebee;
    border: 1px solid #ffcdd2;
    color: #c62828;
    text-align: center;
    border-radius: 5px;
}
</style>