<template>
<div>
  <hazard-details v-if='dataLoaded' v-bind:hazard="this.$store.getters.getAllHazards[1]" /> 
  <manage-hazard v-if='dataLoaded' v-bind:propHazard="this.$store.getters.getAllHazards[1]" />
</div>
</template>

<script>
import HazardDetails from '../components/HazardDetails.vue'
import dataService from "@/services/DataService.js"
import ManageHazard from '../components/ManageHazard.vue'

export default {
  components: { HazardDetails, ManageHazard },
  data(){
    return{
        dataLoaded: false
    }
    },
  mounted() {
    this.reloadData();
  },
  methods:{
    reloadData(){
        dataService.getAllReports().then((response) => {
          this.$store.commit("LOAD_REPORTS", response.data);
          dataService.getAllPotholes().then((response)=> {
            this.$store.commit("LOAD_POTHOLES", response.data);
            dataService.getAllDrains().then((response)=> {
              this.$store.commit("LOAD_DRAINS", response.data);
              this.dataLoaded = true;
            });
          });
        });  
    },
   },
}
</script>

<style>

</style>