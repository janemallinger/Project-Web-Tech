<template>
    <div class="invite-crew">
        <h1>Invite a Crew Member</h1>

        <form @submit.prevent="inviteHandler">
            <div class="group">
                <label for="email">Crew Member Email:</label>
                <input 
                    type="email" 
                    id="email" 
                    v-model="email" 
                    placeholder="Enter Email" 
                    required
                />
            </div>

            <div v-if="error" class="error-message">{{ error }}</div>
            <div v-if="success" class="success-message">{{ success }}</div>

            <button type="submit" :disabled="isSubmitting">Invite Crew Member</button>
        </form>
    </div>
</template>

<script>
export default {
    data() {
        return {
            email: '', 
            error: null,
            success: null,
            isSubmitting: false
        };
    },
    methods: {
        async inviteHandler() {
            this.error = null;
            this.success = null;
            this.isSubmitting = true;

            try {
                const response = await fetch('http://localhost:8080/api/v1/crewMember', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        firstName: 'New',
                        lastName: 'Member',
                        email: this.email,
                        phoneNumber: '1234567890',
                        password: 'temporaryPassword123',
                        role: 'CREW_MEMBER',
                        positions: ['New Position']
                    })
                });

                const result = await response.json();
                
                if (result.flag) {
                    this.success = `Invite sent to ${this.email}`;
                    this.email = '';
                } else {
                    this.error = result.message || 'Failed to send invite';
                }
            } catch (error) {
                this.error = 'An error occurred while sending the invite';
                console.error('Error:', error);
            } finally {
                this.isSubmitting = false;
            }
        }
    }
};
</script>

<style scoped>
.invite-crew {
    max-width: 500px;
    margin: 0 auto;
    padding: 30px;
    color: purple;
    font-family: Arial, sans-serif;
}

.group {
    margin-bottom: 20px;
}

input[type="email"] {
    width: 100%;
    padding: 8px;
    border-radius: 6px;
    border: 1px solid #ccc;
}

button {
    background-color: purple;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;

    &:hover {
        background-color: #7700cc;
    }

    &:disabled {
        background-color: #cccccc;
        cursor: not-allowed;
    }
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