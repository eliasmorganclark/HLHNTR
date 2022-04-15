<template>
<div>
<hazard-details v-bind:hazard="this.$store.state.potholes[0]" /> 
</div>
</template>

<script>
import HazardDetails from '../components/HazardDetails.vue'
import dataService from "@/services/DataService.js"
export default {
  components: { HazardDetails },
mounted() {
    
    this.reloadData();
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
    },
    showMarkerInfoWindow(hazard, id) {
            this.markerInfoWindowPos = hazard.address.coordinates;
            const infoWindowText = hazard.hazardType + ' at ' + hazard.address.houseNumber +
                  ' ' + hazard.address.streetName + ' in ' + hazard.address.city;
            //TODO link to detail about a hazard
            const infoWindowLink = '<a href="/newreport">New Report</a>'
            this.markerInfoOptions.content = infoWindowText + ' ' + infoWindowLink;

            if (this.currentMarkerId == id) {
              this.markerInfoWinOpen = !this.markerInfoWinOpen;
            }
            else {
              this.markerInfoWinOpen = true;
              this.currentMarkerId = id;
            }
          }

},
}
</script>

<style>

</style>