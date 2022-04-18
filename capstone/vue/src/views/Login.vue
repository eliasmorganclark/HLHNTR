<template>
<div id="body">
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
      <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >
        Thank you for registering, please sign in.
      </div>
      <label for="username" class="sr-only">username :</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">password :</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <router-link class="linking" :to="{ name: 'register' }">Need an account?</router-link>
      <button type="submit">Sign in</button>
    </form>
  </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>


<style scoped>

#body {
    font-family: 'Kanit', sans-serif;
    
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  background-color: #222;
}
#login  {
  flex-grow: 1;
  min-height: 80vh;
  margin: 0;
}

.form-signin {
  align-items: center;
  margin: 50px;
  padding: 20px;
  width: 500px;
  display: flex;
  flex-direction: column;
  background-color: white;
  border-radius: 10px;
}

.form-control {
  width: 300px;
  padding: 8px;
  margin: 8px;
  border: 3px solid fuchsia;
  color: fuschia;

}

a {
  text-transform: uppercase;
  color: #444;
  text-decoration: none;


}

button {
   color: #444;
}



</style>