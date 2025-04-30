<template>
    <div class="game-crew-page">
        <h1>Crew for {{ team }} on {{ date }} at {{ time }}</h1>
        <p><Strong>Venue</Strong> {{ venue }}</p>

        <div v-for="member in crewMembers" :key="member.name" class="crew-memeber">
            <h3>{{ member.name }}</h3>
            <h4>{{ member.job }}</h4>
            <p><strong>Clock In Time: </strong>{{ member.time }}</p>
            <p><strong>Meeting Location: </strong>{{ member.location }}</p>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            crewMembers: [
                {
                    job: 'Producer',
                    name: 'Alex Johnson',
                    time: '8:00 AM',
                    location: 'Gate A',
                },
                {
                    job: 'Camera',
                    name: 'Brittany Smith',
                    time: '9:00 AM',
                    location: 'Gate B',
                },
                {
                    job: 'Talent',
                    name: 'Carlson Diaz',
                    time: '10:00 AM',
                    location: 'Gate C',
                },
            ]
        };
    },
    computed: {
        gameId() {
            return this.$route.params.gameId;
        },
        team() {
            return this.$route.query.team;
        },
        date() {
            return this.$route.query.date;
        },
        time() {
            return this.$route.query.time;
        },
        venue() {
            return this.$route.query.venue;
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

.crew-memeber {
    background: #f0f0f0;
    margin: 15px auto;
    padding: 20px;
    border-radius: 10px;
    max-width: 500px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

</style>