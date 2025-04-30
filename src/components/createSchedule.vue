<template>
    <div class="create-schedule">
        <h1>Create Game Schedule</h1>

        <form @submit.prevent="submitSchedule">
            <div class="schedule-characteristics">
                <label>Sport: </label>
                <input type="text" v-model="scheduleData.sport" required />
            </div>

            <div class="schedule-characteristics">
                <label>Season: </label>
                <input type="text" v-model="scheduleData.season" required pattern="^[0-9]{4}-[0-9]{4}$" placeholder="YYYY-YYYY" />
            </div>

            <button type="submit" class="submit-button" :disabled="isSubmitting">Save</button>
        </form>

        <div v-if="error" class="error-message">
            {{ error }}
        </div>

        <div v-if="success" class="success-message">
            Game schedule created successfully
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            scheduleData: {
                sport: '',
                season: ''
            },
            isSubmitting: false,
            error: null,
            success: false
        };
    },
    methods: {
        async submitSchedule() {
            this.isSubmitting = true;
            this.error = null;
            this.success = false;

            try {
                const response = await fetch('http://localhost:8080/api/v1/gameSchedule', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(this.scheduleData)
                });

                const result = await response.json();

                if (result.flag) {
                    this.success = true;
                    this.scheduleData = {
                        sport: '',
                        season: ''
                    };
                    setTimeout(() => {
                        this.$router.push({ name: 'gameSchedule' });
                    }, 1500);
                } else {
                    this.error = result.message || 'Failed to create schedule';
                }
            } catch (error) {
                this.error = 'Failed to create schedule. Please try again.';
                console.error('Error creating schedule:', error);
            } finally {
                this.isSubmitting = false;
            }
        }
    }
};
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

input, select {
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
}

.submit-button:hover {
    background-color: #7700cc;
}

.submit-button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
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
    background-color: #ffebee;
    border: 1px solid #ffcdd2;
    color: #c62828;
    text-align: center;
    border-radius: 5px;
}
</style>