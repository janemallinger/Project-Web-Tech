<template>
    <div class="registration-form">
      <h1>Register for a New Account</h1>
      <form @submit.prevent="handleSubmit">
        <div>
          <label for="name">Name</label>
          <input type="text" v-model="formData.name" required />
        </div>
        <div>
          <label for="email">Email</label>
          <input type="email" v-model="formData.email" required />
        </div>
        <div>
          <label for="password">Password</label>
          <input type="password" v-model="formData.password" required />
        </div>
        <button type="submit">Next</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        formData: {
          name: '',
          email: '',
          password: '',
        },
      };
    },
    methods: {
      handleSubmit() {
        console.log('Button clicked, navigating...', this.formData);
        this.$router.push({ name: 'confirmation', query: this.formData });
      },
    },
  };
  </script>
    <style scoped>
        .registration-form {
    font-family: 'Arial', sans-serif;
    background-color: #f8f8f8;
    color: purple;
    margin: 0;
    padding: 50px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 80vh;
    }

    h1 {
    color: purple;
    margin-bottom: 20px;
    }

    form {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 15px;
    }

    label {
    font-size: 14px;
    margin-bottom: 5px;
    }

    input {
    padding: 10px;
    font-size: 14px;
    width: 250px;
    border: 1px solid #ccc;
    border-radius: 5px;
    }

    button {
    padding: 10px 20px;
    font-size: 16px;
    background-color: purple;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    }

    button:hover {
    background-color: darkviolet;
    }
  </style>