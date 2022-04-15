<template>
  <div>
      <GmapMap
      :center='mapCenter'
      :zoom='12'
      :options="{
        zoomControl: true,
        mapTypeControl: true,
        scaleControl: false,
        streetViewControl: false,
        rotateControl: false,
        fullscreenControl: true,
        disableDefaultUi: false
      }"
      style='width:100%;  height: 800px;'
    >
        <gmap-info-window 
          :options="markerInfoOptions" 
          :position="markerInfoWindowPos" 
          :opened="markerInfoWinOpen" 
          @closeclick="markerInfoWinOpen=false"
        />
      
        <GmapMarker
            v-for="hazard in this.$store.getters.getAllHazards"
            :key="hazard.hazardId"
            :position="hazard.address.coordinates"
            :icon="hazard.iconPath"
            :clickable="true"
            @click="showMarkerInfoWindow(hazard, hazard.hazardId)" 
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
        mapCenter:{lat: 41.4993, lng: -81.6944},
        markerInfoWindowPos: null,
        markerInfoWinOpen: false,
        currentMarkerId: null,
        markerInfoOptions: {
          content: '',
            pixelOffset: {
              width: 0,
              height: -35
            }
         },
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
    },
    showMarkerInfoWindow(hazard, id) {
            this.markerInfoWindowPos = hazard.address.coordinates;
            this.markerInfoOptions.content = 
                  hazard.hazardType + ' at ' + hazard.address.houseNumber +
                  ' ' + hazard.address.streetName + ' in ' + hazard.address.city;

            if (this.currentMarkerId == id) {
              this.markerInfoWinOpen = !this.markerInfoWinOpen;
            }
            else {
              this.markerInfoWinOpen = true;
              this.currentMarkerId = id;
            }
          }

},
created(){
    this.reloadData();
}
}

</script>

<style>

</style>