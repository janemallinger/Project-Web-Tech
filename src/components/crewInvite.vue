<template>
    <div class="invite-crew">
        <h1>Invite a Crew Member</h1>

        <form @submit.prevent="inviteHandler">
            <div class="group">
                <label for="email">Crew Member Email:</label>
                <input 
                    type = "email" id="email" v-model="email" placeholder="Enter Email" required
                />
            </div>

            <div v-if="error" class="error">{{ error }}</div>
            <div v-if="success" class="success">{{ success }}</div>

            <button type="submit">Invite Crew Member</button>
        </form>

    </div>
</template>

<script >
export default {
    data() {
        return {
            email: '', 
            error: '',
            success: '',   
        };
    },
    methods: {
        async inviteHandler() {
            this.error = ''
            this.success = ''

            try {
                const response = await fetch('/api/admin/invite', {
                    method: 'POST',
                    Headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ email: this.email })
                })

                if (!response.ok) {
                    const errorData = await response.json()
                    this.error = errorData.message || 'Failed to send invite.'
                    return
                }
                
                this.success = `Invite sent to ${this.email}`
                this.email = ''
                
            } catch (error) {
                this.error = 'An error occured while sending the invite'
                console.error(error)
                
            }
        }
    }
}

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
    border: 1px solid;
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
}

.error {
    color: lightcoral;
    margin-bottom: 10px;
}

.success {
    color: lightgreen;
    margin-bottom: 10px;
}

</style>