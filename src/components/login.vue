<template>
  <div class="login-page">
      <h1>Login</h1>
      <form @submit.prevent="handleSubmit">
          <div>
              <label for="email">Email </label>
              <input type="email" v-model="formData.email" required />
          </div>
          <div>
              <label for="password">Password </label>
              <input type="password" v-model="formData.password" required />
          </div>
          <div v-if="error" class="error-message">{{ error }}</div>
          <div>
              <button type="submit" :disabled="isSubmitting">Login</button>
          </div>
          <div class="register-link">
              Don't have an account? <router-link to="/register">Register here</router-link>
          </div>
      </form>
  </div>
</template>

<script>
export default {
  data() {
      return {
          formData: {
              email: '',
              password: ''
          },
          isSubmitting: false,
          error: null
      }
  },
  methods: {
      async handleSubmit() {
          this.isSubmitting = true;
          this.error = null;

          try {
              // Temporary bypass for testing
              if (this.formData.email === 'admin@example.com' && this.formData.password === 'admin123') {
                  localStorage.setItem('userRole', 'ADMIN');
                  localStorage.setItem('userId', '1');
                  this.$router.push('/home');
                  return;
              }

              if (this.formData.email === 'crew@example.com' && this.formData.password === 'crew123') {
                  localStorage.setItem('userRole', 'CREW_MEMBER');
                  localStorage.setItem('userId', '2');
                  this.$router.push('/home');
                  return;
              }

              const response = await fetch('http://localhost:8080/api/v1/auth/login', {
                  method: 'POST',
                  headers: {
                      'Content-Type': 'application/json'
                  },
                  body: JSON.stringify(this.formData)
              });

              const result = await response.json();

              if (result.flag) {
                  localStorage.setItem('userRole', result.data.role);
                  localStorage.setItem('userId', result.data.userId);
                  
                  if (result.data.role === 'ADMIN') {
                      this.$router.push('/home');
                  } else {
                      this.$router.push('/home');
                  }
              } else {
                  this.error = result.message || 'Login failed';
              }
          } catch (error) {
              this.error = 'Failed to login. Please try again.';
              console.error('Login error:', error);
          } finally {
              this.isSubmitting = false;
          }
      }
  }
}
</script>

<style scoped>
.login-page {
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
  background-color: #7700cc;
}

button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.error-message {
  color: #c62828;
  background-color: #ffebee;
  padding: 10px;
  border-radius: 5px;
  margin: 10px 0;
  text-align: center;
}

.register-link {
  margin-top: 20px;
  font-size: 14px;
}

.register-link a {
  color: purple;
  text-decoration: none;
}

.register-link a:hover {
  text-decoration: underline;
}
</style>