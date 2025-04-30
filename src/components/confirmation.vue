<template>
  <div class="registration-confirmation">
    <h1>Confirm Your Registration</h1>
    <div>
      <p><strong>Name:</strong> {{ formData.name }}</p>
      <p><strong>Email:</strong> {{ formData.email }}</p>
      <p><strong>Password:</strong> {{ formData.password }}</p>
      <p><strong>Positions:</strong> {{ formData.positions }}</p>
    </div>
    <button @click="confirmRegistration">Confirm</button>
    <button @click="modifyDetails">Modify</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formData: this.$route.query, 
    };
  },
  methods: {
    async confirmRegistration() {
      try {
        const response = await fetch('http://localhost:8080/api/v1/crewMember', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            firstName: this.formData.name.split(' ')[0],
            lastName: this.formData.name.split(' ')[1] || '',
            email: this.formData.email,
            password: "000-000-0000", 
            role: "Crew",
            positions: ["Camera"],
            phoneNumber: "0000000000",
          })
        })

        const result = await response.json()

        if(response.ok && result.flag) {
          this.$router.push('/success')
        } else {
          alert(result.message || "Registration failed")
        }
      } catch (error) {
        console.error('Registartion error: ', error)
        alert("Error submitting registration")
        
      }

    },
    modifyDetails() {
      this.$router.push('/');
    },
  },
};
</script>
<style scoped>
.registration-confirmation {
font-family: 'Arial', sans-serif;
background-color: #f8f8f8;
color: purple;
margin: 0;
padding: 50px;
display: flex;
flex-direction: column;
justify-content: center;
align-items: center;
height: 90vh;
}

h1 {
color: purple;
margin-bottom: 20px;
}

div {
margin-bottom: 20px;
font-size: 16px;
}

p {
margin: 5px 0;
}

button {
padding: 10px 20px;
font-size: 16px;
background-color: purple;
color: white;
border: none;
border-radius: 5px;
cursor: pointer;
margin: 5px;

&:hover {
background-color: #7700cc;
}
}


</style>