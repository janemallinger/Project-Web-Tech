<template>
    <div class="registration-confirmation">
      <h1>Confirm Your Registration</h1>
      <div>
        <p><strong>Name:</strong> {{ formData.name }}</p>
        <p><strong>Email:</strong> {{ formData.email }}</p>
        <p><strong>Password:</strong> {{ formData.password }}</p>
      </div>
      <button @click="confirmRegistration">Confirm</button>
      <button @click="modifyDetails">Modify</button>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        formData: this.$route.query, // Retrieve data from query params
      };
    },
    methods: {
      confirmRegistration() {
        // Send data to the backend, and navigate to the success page
        this.$router.push('/success');
      },
      modifyDetails() {
        // Navigate back to the registration form to allow the user to modify their data
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
    }

    button:hover {
    background-color: darkviolet;
    }
  </style>