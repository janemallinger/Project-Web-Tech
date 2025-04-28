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

            <button type="submit" class="submit-button">Add Game</button>
        </form>

        <div v-if="saved" class="success-message">
            New game added successfully
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
                requiredCrew: ''
            },
            saved: false,
        }
    },
    methods: {
        submitNewGame() {
            if(!this.newGame.sport || !this.newGame.dateTime || !this.newGame.location || !this.newGame.requiredCrew) {
                alert('Fill out all required fields.')
                return;
            }

            console.log('New game to be added: ', this.newGame)
            this.saved = true;

            this.newGame = {
                sport: '',
                dateTime: '',
                location: '',
                opponent: '',
                requiredCrew: ''
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
</style>