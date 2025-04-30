<template>
    <div class="game-crew-page">
        <h1>Crew for vs {{ opponent }} on {{ date }}</h1>
        <p><strong>Venue</strong> {{ venue }}</p>

        <div v-if="error" class="error-message">{{ error }}</div>

        <div v-if="crewMembers.length">
            <div v-for="member in crewMembers" :key="member.userId" class="crew-member">
                <h3>{{ member.fullName }}</h3>
                <h4>{{ member.position }}</h4>
                <p><strong>Email:</strong> {{ member.email }}</p>
                <p><strong>Phone:</strong> {{ member.phoneNumber }}</p>
                <p><strong>Report Time:</strong> {{ member.reportTime }}</p>
                <p><strong>Report Location:</strong> {{ member.reportLocation }}</p>
            </div>
        </div>
        <div v-else>
            <p>No crew members assigned yet</p>
        </div>

        <div v-if="isAdmin" class="assign-crew">
            <router-link 
                :to="{ 
                    name: 'scheduleCrew',
                    query: { 
                        gameId: gameId,
                        opponent: opponent,
                        date: date,
                        venue: venue
                    }
                }"
                class="assign-button"
            >
                Assign Crew Members
            </router-link>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            crewMembers: [],
            error: null,
            isAdmin: localStorage.getItem('userRole') === 'ADMIN',
            crewAssignments: [],
            gameDetails: null
        };
    },
    computed: {
        gameId() {
            return this.$route.params.id;
        },
        opponent() {
            return this.$route.query.opponent;
        },
        date() {
            return this.$route.query.date;
        },
        venue() {
            return this.$route.query.venue;
        },
        formattedCrew() {
            return this.crewAssignments.map(assignment => ({
                ...assignment,
                reportTime: assignment.reportTime || 'Not specified',
                reportLocation: assignment.reportLocation || 'Not specified'
            }));
        }
    },
    async created() {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/crewList/${this.gameId}`);
            const result = await response.json();
            
            if (result.flag) {
                this.crewMembers = result.data.crewedMembers || [];
            } else {
                this.error = result.message;
            }
        } catch (error) {
            this.error = 'Failed to fetch crew assignments';
            console.error('Error:', error);
        }
    }
};

</script>

<style scoped>

.game-crew-page {
    text-align: center;
    font-family: 'Arial', sans-serif;
    padding: 30px;
    color: purple;
}

.crew-member {
    background: #f0f0f0;
    margin: 15px auto;
    padding: 20px;
    border-radius: 10px;
    max-width: 500px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
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

.assign-crew {
    margin-top: 30px;
}

.assign-button {
    display: inline-block;
    padding: 12px 24px;
    background-color: purple;
    color: white;
    text-decoration: none;
    border-radius: 5px;
    font-weight: bold;
    transition: background-color 0.3s;
}

.assign-button:hover {
    background-color: #7700cc;
}
</style>