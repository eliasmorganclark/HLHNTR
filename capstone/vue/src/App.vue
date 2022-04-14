<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link
        v-bind:to="{ name: 'logout' }"
        v-if="$store.state.token != ''"
        >Logout</router-link
      >
      <router-link v-bind:to="{ name: 'NewReportView' }"
        >Report Hazard</router-link
      >
    </div>
    <router-view />
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

