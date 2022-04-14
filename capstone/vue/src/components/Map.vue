<template>
  <div>
      <GmapMap
      :center='mapCenter'
      :zoom='12'
      style='width:100%;  height: 400px;'
    >
    <GmapMarker
        v-for="pothole in this.$store.state.potholes"
        :key="pothole.hazardId"
        :position="pothole.address.coordinates"
      />
      </GmapMap>
  </div>
</template>

<script>
import dataService from "@/services/DataService.js";
export default {
    name:"Map",
data(){
    return{
        mapCenter:{lat: 41.4993, lng: -81.6944}
    }
},
 mounted() {
    this.geolocate();
  },
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
    },
    geolocate() {
      navigator.geolocation.getCurrentPosition(position => {
        this.mapCenter = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };
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