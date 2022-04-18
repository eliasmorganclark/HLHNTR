<template>
  <div id="app">
    <div id="nav">
       <router-link v-bind:to="{ name: 'home' }">
      <span class="title">
        <img src="@/img/logo.png" /> 
        <h1> HLHNTR </h1> 
        </span>
        </router-link>
        <span class ="links">
     
     <router-link 
        v-bind:to="{ name: 'login' }" 
        v-if="$store.state.token == ''"
        >LOGIN</router-link>
      <router-link
        v-bind:to="{ name: 'logout' }"
        v-if="$store.state.token != ''"
        >Logout</router-link
      > 
      <router-link v-bind:to="{ name: 'NewReportView' }"
        >Report Hazard</router-link
      >
      </span>
    </div>
<div id= "body">


    <router-view />
</div>
    <footer> 
      <p> Hi mom! </p>
         </footer>
  </div>
</template>

<script>
// import dataService from "@/services/DataService.js";

export default {
  components: { },
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

@import url('https://fonts.googleapis.com/css2?family=Kanit:ital,wght@0,100;0,400;0,900;1,100;1,400;1,900&display=swap');

#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
 
 

}

#body {
  flex-grow: 1;
}

#nav {
  font-family: 'Kanit', sans-serif;
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
  padding:  30px;
}


#nav a {
  text-transform: uppercase;
  text-decoration:  none;
  font-size: 20px;
  color: #666;
  letter-spacing:  1.5px;
  font-weight: 700;
  padding: 0 7px;
}


.title img {
  /* border: 3px solid red; */
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
}

footer {
  font-family: 'Kanit', sans-serif;
  display: flex;
  margin-top: auto;
  justify-content: space-between;
  height: 50px;
  width: 100%;

  background-color: #111;

}


</style>