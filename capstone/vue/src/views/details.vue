<template>
  <div v-if="dataLoaded" id="PLEASE">
    <hazard-details v-if="dataLoaded" v-bind:hazard="currentHazard" />
    <manage-hazard
      v-if="isAdmin"
      v-bind:propHazard="currentHazard"
    />
  </div>
</template>

<script>
import HazardDetails from "../components/HazardDetails.vue";
import dataService from "@/services/DataService.js";
import ManageHazard from "../components/ManageHazard.vue";

export default {
  components: { HazardDetails, ManageHazard },
  data() {
    return {
      dataLoaded: false,
      currentHazard: {},
    };
  },
  computed:{
    isAdmin(){
      if(this.$store.state.user.authorities != null){
        return this.$store.state.user.authorities[0].name == 'ROLE_ADMIN'
      }
      return false;
    }
  },
  mounted() {
    this.loadData();
  },
  methods: {
    loadData() {
      dataService.getHazard(this.$route.params.hazardId).then((response) => {
        this.currentHazard = response.data;
        this.dataLoaded = true;
      }).catch((e) => {console.log(e)});
    },
  },
};
</script>

<style>

  </style>
