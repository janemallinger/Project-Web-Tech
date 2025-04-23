<template>
    <div class="sign-up">
        <h1>Submit Availability</h1>

        <form @submit.prevent="handleSubmit">
            <div class="game-card" v-for="game in games" :key="game.id">
                <h3>{{ game.date }} - {{ game.team }}</h3>
                <p><strong>Time: </strong> {{ game.time }}</p>
                <p><strong>Venue: </strong> {{ game.venue }}</p>

                <label>
                    <input type="checkbox" v-model="availability[game.id].available" />I can work this game
                </label>
                    <textarea v-model="availability[game.id].comment" placeholder="Further Information"></textarea>
                </div>

                <button type="submit" class="submit-button">Submit Availability</button>
        </form>

        <div v-if="submitted" class="success-message">You have submitted your availability!</div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                submitted: false,
                games: [
                    {
                        id: 1,
                        date: '05/01/2025',
                        time: '10:00 AM',
                        team: 'Team B',
                        venue: 'Stadium A',
                    },
                    {
                        id: 2,
                        date: '05/03/2025',
                        time: '02:00 PM',
                        team: 'Team A',
                        venue: 'Stadium B',
                    },
                    {
                        id: 3,
                        date: '05/05/2025',
                        time: '01:30 PM',
                        team: 'Team C',
                        venue: 'Stadium C',
                    }
                ],
                availability: {},
            };
        },
        created() {
            this.games.forEach(game => {
                this.availability[game.id] = { available: false, comment: ''};
            });
        },
        methods: {
            handleSubmit() {
                const selected = this.games.filter(game => this.availability[game.id].available);

                if (selected.length === 0) {
                    alert('Must mark at least one game as available.');
                    return;
                }

                console.log('Submitted availability:', this.availability);
                this.submitted = true;
            }
        }
    };

</script>

<style scoped>
.sign-up {
    padding: 30px;
    max-width: 600px;
    margin: 0 auto;
    color: purple;
    font-family: Arial, sans-serif;
}

.game-card {
    background: #f8f8f8;
    padding: 20px;
    margin-bottom: 20px;
    border-radius: 10px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

textarea {
    display: block;
    width: 100%;
    margin-top: 10px;
    padding: 8px;
    border-radius: 6px;
    border: 1px solid #ccc;
    resize: vertical;
}

.submit-button {
    background-color: purple;
    color: white;
    border: none;
    padding: 10px 20px;
    font-weight: bold;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 20px;

    &:hover {
    background-color: #7700cc;
    }
}


.success-message {
    margin-top: 20px;
    padding: 10px;
    background-color: lightgreen;
    border: 1px solid #70db70;
    color: #207520;
    text-align: center;
    border-radius: 5px;
}


</style>