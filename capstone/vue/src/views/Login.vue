<template>
<div id="body">
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
      <h2 class="h3 mb-3 font-weight-normal">Please Sign In to HLHNTR:</h2>
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
      <label for="username" class="sr-only">USERNAME :</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="USERNAME"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">PASSWORD :</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="PASSWORD"
        v-model="user.password"
        required
      />
      <br />
      <router-link class="linking" :to="{ name: 'register' }"> ( Need an account? Click here! )</router-link>
      <button class="submitty" type="submit">SIGN IN</button>
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


h2 {
  font-family: 'Kanit', sans-serif;
  letter-spacing: 2px;

}


#body {
    font-family: 'Kanit', sans-serif;
    
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  background-image: url('../img/potholebg.jpg');
  
  background-color: black;
}
#login  {
  flex-grow: 1;
  min-height: 80vh;
  margin: 0;
}

.form-signin {
   font-family: 'Kanit', sans-serif;
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
   font-family: 'Kanit', sans-serif;
   letter-spacing: 1px;
  width: 300px;
  padding: 8px;
  margin: 8px;
  border: 3px solid #888;
  border-radius: 10px;
  background-color: white;
  color: fuchsia;

}

a {
  text-transform: uppercase;
  color: FUCHSIA;
  text-decoration: none;


}

.submitty {
  letter-spacing: 3px;
   font-family: 'Kanit', sans-serif;
  font-size: 30px;
  text-transform: uppercase;
  border: 0;
  box-shadow: none;
  background-color: black;
   color: fuchsia;
   padding: 5px;
   margin: 20px;
   width: 200px;
   border-radius: 10px;
}



</style>