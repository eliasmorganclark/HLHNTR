<template>
  <div>
      {{this.$store.state.reports}}
  </div>
</template>

<script>
import dataService from "@/services/DataService.js";
export default {
    name:"Map",
methods:{
    reloadData(){
        dataService.getAllReports().then((response) => {
        this.$store.commit("LOAD_REPORTS", response.data);
        });
        dataService.getAllPotholes().then((response)=> {
        this.$store.commit("LOAD_POTHOLES", response.data);
        });
        dataService.getAllDrains().then((response)=> {
        this.$store.commit("LOAD_DRAINS", response.data);
        });
    }
},
created(){
    this.reloadData();
}
}

</script>

<style>

</style>