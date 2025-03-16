<template>
    <div class="registration-success">
      <h1>Registration Successful!</h1>
      <p>Your account has been created successfully.</p>
    </div>
  </template>
  
  <script>
  </script>
  
  <style scoped>
    .registration-success {
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

    p {
    font-size: 16px;
    margin-top: 10px;
    }

    button {
    padding: 10px 20px;
    font-size: 16px;
    background-color: purple;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 20px;
    }

    button:hover {
    background-color: darkviolet;
    }
  </style>