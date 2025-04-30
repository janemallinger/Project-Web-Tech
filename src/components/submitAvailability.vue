<template>
    <div class="sign-up">
        <h1>Submit Availability</h1>

        <form @submit.prevent="handleSubmit">
            <div class="game-card" v-for="game in games" :key="game.gameId">
                <h3>{{ formatDate(game.gameDate) }} - vs {{ game.opponent }}</h3>
                <p><strong>Venue: </strong> {{ game.venue }}</p>

                <label>
                    <input type="checkbox" v-model="availability[game.gameId].available" />I can work this game
                </label>
                <textarea v-model="availability[game.gameId].comment" placeholder="Further Information"></textarea>
            </div>

            <button type="submit" class="submit-button" :disabled="isSubmitting">Submit Availability</button>
        </form>

        <div v-if="error" class="error-message">{{ error }}</div>
        <div v-if="success" class="success-message">You have submitted your availability!</div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            games: [],
            availability: {},
            isSubmitting: false,
            error: null,
            success: false
        };
    },
    async created() {
        this.games = [
            {
                gameId: 1,
                gameDate: '02/25/2026',
                opponent: 'Team B',
                venue: 'Main Stadium'
            },
            {
                gameId: 2,
                gameDate: '08/08/2025',
                opponent: 'Team A',
                venue: 'West Field'
            }
        ]

        this.games.forEach(game => {
            this.availability[game.gameId] = {
                available: false,
                comment: '',
                gameId: game.gameId
            }
        })
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
        async handleSubmit() {
            this.isSubmitting = true;
            this.error = null;
            this.success = false;

            try {
                const userId = 11; 
                const availabilityData = {
                    userId,
                    availabilities: Object.values(this.availability)
                };

                console.log('Mock submit:', availabilityData);

                await new Promise(resolve => setTimeout(resolve, 500));

                this.success = true;

                this.games.forEach(game => {
                    this.availability[game.gameId] = {
                        available: false,
                        comment: '',
                        gameId: game.gameId
                    };
                });

            } catch (error) {
                this.error = 'Failed to submit availability. Please try again.';
                console.error('Error:', error);
            } finally {
                this.isSubmitting = false;
            }
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