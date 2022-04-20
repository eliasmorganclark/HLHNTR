<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">
        <span class="title">
          <img src="@/img/logo.png" />
          <h1>HLHNTR</h1>
          <h4>: A HAZARD TRACKER</h4>
        </span>
      </router-link>
      <span class="links">
        <span class="user-name" v-if="$store.state.token != ''">
          HELLO, {{ $store.state.user.username }} !
        </span>
        <a href="/newreport">Report Hazard </a> 

        <router-link
          v-bind:to="{ name: 'login' }"
          v-if="$store.state.token == ''"
          >LOGIN</router-link
        >
        <router-link
          v-bind:to="{ name: 'logout' }"
          v-if="$store.state.token != ''"
          >LOGOUT</router-link
        >
      </span>
    </div>
    <div id="body">
      <router-view />
    </div>
    <footer id="bottom">
      <p>
        PROUDLY SERVING NORTHEAST OHIO SINCE 2022, WITH A SPECIAL THANKS TO OUR
        FRIENDS AND MENTORS AT TECH ELEVATOR CLEVELAND COHORT 19!
      </p>
    </footer>
  </div>
</template>

<script>
// import dataService from "@/services/DataService.js";

export default {
  components: {},
  // created() {
  //   dataService.getAllReports().then((response) => {
  //     this.$store.commit("LOAD_REPORTS", response.data);
  //   });
  //   dataService.getAllPotholes().then((response)=> {
  //     this.$store.commit("LOAD_POTHOLES", response.data);
  //   });
  //   dataService.getAllDrains().then((response)=> {
  //     this.$store.commit("LOAD_DRAINS", response.data);
  //   });
  // },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Kanit:ital,wght@0,100;0,400;0,900;1,100;1,400;1,900&display=swap");

#app {
  /* min-height: 100vh; */
  display: flex;
  flex-direction: column;
}

#body {
  /* height: 1fr; */
  /* flex-grow: 5; */
}

#nav {
  font-family: "Kanit", sans-serif;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100px;
  width: 100%;

  background-color: #111;
}
span.title {
  /* border: 3px solid hotpink; */
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 0 0 20px;
  height: 100%;
}
span.links {
  padding: 30px;
  margin: 30px 0 0 0;
}

#nav a:hover {
  color: fuchsia;
}

.user-name,
#nav a {
  text-transform: uppercase;
  text-decoration: none;
  font-size: 20px;
  color: #666;
  letter-spacing: 3px;
  font-weight: 700;
  padding: 0 7px;
}

.title img {
  /* border: 3px solid red; */
  margin: 0 15px 0 0;
  height: 80px;
  padding: 15px;
}

h1 {
  margin: -15px;
  padding: 0;
  letter-spacing: 5px;
  font-weight: 900;
  color: #ccc;
  font-size: 90px;
  text-shadow: 2px 2px fuchsia;
}

footer {
  font-family: "Kanit", sans-serif;
  display: flex;
  margin-top: auto;
  align-items: center;
  justify-content: center;
  height: 50px;
  width: 100%;
  color: #666;
  font-weight: 400;
  letter-spacing: 2px;
  background-color: #111;
}

h4 {
  margin: 30px 0 0 30px;
  padding: 0;
  letter-spacing: 3px;
  font-weight: 900;
  color: #666;
  font-size: 20px;
}
</style>
