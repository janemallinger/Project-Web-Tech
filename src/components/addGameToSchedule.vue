<template>
    <div class="add-game">
        <h1>Add New Game</h1>

        <form @submit.prevent="submitNewGame">
            <div class="game-characteristics">
                <label>Sport: </label>
                <input type="text" v-model="newGame.sport" required />
            </div>

            <div class="game-characteristics">
                <label>Date and Time: </label>
                <input type="datetime-local" v-model="newGame.dateTime" required />
            </div>

            <div class="game-characteristics">
                <label>Location: </label>
                <input type="text" v-model="newGame.location" required />
            </div>

            <div class="game-characteristics">
                <label>Opponent: </label>
                <input type="text" v-model="newGame.opponent" />
            </div>

            <div class="game-characteristics">
                <label>Required Crew: </label>
                <input type="text" v-model="newGame.requiredCrew" required />
            </div>

            <div class="game-characteristics">
                <label>Finalize Game: </label>
                <input type="checkbox" v-model="newGame.isFinalized" />
            </div>

            <button type="submit" class="submit-button">Add Game</button>
        </form>

        <div v-if="success" class="success-message">
            New game added successfully
        </div>

        <div v-if="error" class="error-message">
            {{ error }}
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            newGame: {
                sport: '',
                dateTime: '',
                location: '',
                opponent: '',
                requiredCrew: '',
                isFinalized: false
            },
            isSubmitting: false,
            error: null,
            success: false
        }
    },
    methods: {
        async submitNewGame() {
            if(!this.newGame.sport || !this.newGame.dateTime || !this.newGame.location || !this.newGame.requiredCrew) {
                this.error = 'Please fill out all required fields.';
                return;
            }

            this.isSubmitting = true;
            this.error = null;
            this.success = false;

            try {
                const scheduleResponse = await fetch('http://localhost:8080/api/v1/gameSchedule', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        sport: this.newGame.sport,
                        season: `${new Date().getFullYear()}-${new Date().getFullYear() + 1}`
                    })
                });

                if (!scheduleResponse.ok) {
                    const scheduleError = await scheduleResponse.text();
                    console.error('Schedule creation failed:', scheduleError);
                    throw new Error(`Failed to create schedule: ${scheduleError}`);
                }

                const scheduleResult = await scheduleResponse.json();
                console.log('Schedule created:', scheduleResult);
                const scheduleId = scheduleResult.data.scheduleId;

                const formattedDate = new Date(this.newGame.dateTime).toISOString();

                const gameResponse = await fetch(`http://localhost:8080/api/v1/gameSchedule/${scheduleId}/games`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        opponent: this.newGame.opponent,
                        gameDate: formattedDate,
                        venue: this.newGame.location,
                        isFinalized: this.newGame.isFinalized
                    })
                });

                if (!gameResponse.ok) {
                    const gameError = await gameResponse.text();
                    console.error('Game creation failed:', gameError);
                    throw new Error(`Failed to add game: ${gameError}`);
                }

                const gameResult = await gameResponse.json();
                console.log('Game added:', gameResult);

                if (gameResult.flag) {
                    this.success = true;
                    this.newGame = {
                        sport: '',
                        dateTime: '',
                        location: '',
                        opponent: '',
                        requiredCrew: '',
                        isFinalized: false
                    };
                    setTimeout(() => {
                        this.$router.push({ name: 'gameSchedule' });
                    }, 1500);
                } else {
                    this.error = gameResult.message || 'Failed to add game';
                }
            } catch (error) {
                console.error('Full error:', error);
                this.error = error.message || 'Failed to add game. Please try again.';
            } finally {
                this.isSubmitting = false;
            }
        }
    }
}

</script>

<style scoped>

.add-game {
    padding: 30px;
    max-width: 600px;
    margin: 0 auto;
    color: purple;
    font-family: 'Arial', sans-serif;
}

h1 {
    text-align: center;
    color: purple;
}

.game-characteristics {
    margin-bottom: 20px;
}

label {
    font-weight: bold;
}

input {
    width: 100%;
    padding: 8px;
    margin-top: 5px;
    border-radius: 6px;
    border: 1px solid #ccc;
}

.submit-button {
    background-color: purple;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    display: block;
    margin: 0 auto;

    &:hover {
        background-color: #7700cc;
    }
}

.success-message {
    margin-top: 20px;
    padding: 10px;
    background-color: lightgreen;
    border: 1px solid #70db70;
    color: #207250;
    text-align: center;
    border-radius: 5px;
}

.error-message {
    margin-top: 20px;
    padding: 10px;
    background-color: #ffd7d7;
    border: 1px solid #ff7070;
    color: #722020;
    text-align: center;
    border-radius: 5px;
}
</style>