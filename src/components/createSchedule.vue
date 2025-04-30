<template>
    <div class="create-schedule">
        <h1>Create Game Schedule</h1>

        <form @submit.prevent="submitSchedule">
            <div class="schedule-characteristics">
                <label>Date: </label>
                <input type="date" v-model="scheduleData.date" required />
            </div>

            <div class="schedule-characteristics">
                <label>Time: </label>
                <input type="time" v-model="scheduleData.time" required />
            </div>

            <div class="schedule-characteristics">
                <label>Location: </label>
                <input type="text" v-model="scheduleData.venue" required />
            </div>

            <div class="schedule-characteristics">
                <label>Opponent: </label>
                <input type="text" v-model="scheduleData.team" />
            </div>

            <div class="schedule-characteristics">
                <label>Required Crew: </label>
                <input type="text" v-model="scheduleData.crewPositions" required />
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
            scheduleData: {
                date: '',
                time: '',
                team: '',
                venue: '',
                crewPositions: ['Camera', 'Talent'],
            },
            saved: false
        };
    },
  methods: {
    async submitSchedule() {
      try {
        const response = await fetch('http://localhost:8080/api/createSchedule', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.scheduleData),
        });

        if (!response.ok) {
          throw new Error('Failed to create schedule');
        }

        this.saved = true

        this.$router.push({ name: 'gameSchedule' })

        this.scheduleData = {
            date: '',
            time: '',
            team: '',
            venue: '',
            crewPositions: '',
        }

        this.$router.push({ name: 'gameSchedule' });
      } catch (error) {
        console.error('Error:', error);
        alert('Error creating schedule');
      }

    //         newSchedule: {
    //             sport: '',
    //             dateTime: '',
    //             location: '',
    //             opponent: '',
    //             requiredCrew: ''
    //         },
    //         saved: false,
    //     }
    // },
    // methods: {
    //     submitSchedule() {
    //         if(!this.newSchedule.sport || !this.newSchedule.dateTime || !this.newSchedule.location || !this.newSchedule.requiredCrew) {
    //             alert('Fill out all required fields.')
    //             return;
    //         }
    //         console.log('Schedule saved:', this.newSchedule)
    //         this.saved = true

    //         this.newSchedule = {
    //             sport: '',
    //             dateTime: '',
    //             location: '',
    //             opponent: '',
    //             requiredCrew: ''
    //         }
        }
    }
}

</script>

<style scoped>

.create-schedule {
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
    margin-top: 20px;
    padding: 10px;
    background-color: lightgreen;
    border: 1px solid #70db70;
    color: #207250;
    text-align: center;
    border-radius: 5px;
}
</style>