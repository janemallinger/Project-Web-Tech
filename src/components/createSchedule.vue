<template>
    <div class="create-schedule">
        <h1>Create Game Schedule</h1>

        <form @submit.prevent="submitSchedule">
            <div class="schedule-characteristics">
                <label>Sport: </label>
                <input type="text" v-model="newSchedule.sport" required />
            </div>

            <div class="schedule-characteristics">
                <label>Date and Time: </label>
                <input type="datetime-local" v-model="newSchedule.dateTime" required />
            </div>

            <div class="schedule-characteristics">
                <label>Location: </label>
                <input type="text" v-model="newSchedule.location" required />
            </div>

            <div class="schedule-characteristics">
                <label>Opponent: </label>
                <input type="text" v-model="newSchedule.opponent" />
            </div>

            <div class="schedule-characteristics">
                <label>Required Crew: </label>
                <input type="text" v-model="newSchedule.requiredCrew" required />
            </div>

            <button type="submit" class="submit-button">Save</button>
        </form>

        <div v-if="saved" class="success">
            Game schedule was saved as a draft
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            newSchedule: {
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
        submitSchedule() {
            if(!this.newSchedule.sport || !this.newSchedule.dateTime || !this.newSchedule.location || !this.newSchedule.requiredCrew) {
                alert('Fill out all required fields.')
                return;
            }
            console.log('Schedule saved:', this.newSchedule)
            this.saved = true

            this.newSchedule = {
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

.create-schedule {
    max-width: 600px;
    margin: 50px auto;
    padding: 20px;
    background-color: #f8f8f8;
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    font-family: 'Arial', sans-serif;
}

h1 {
    text-align: center;
    color: purple;
}

.schedule-characteristics {
    margin-bottom: 20px;
}

label {
    display: block;
    font-weight: bold;
    margin-bottom: 6px;
}

input {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 5px;
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

.success {
    text-align: center;
    color: lightgreen;
    margin-top: 20px;
    font-weight: bold;
}
</style>