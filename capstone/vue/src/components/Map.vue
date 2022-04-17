<template>
  <div>
    <GmapMap
      :center="initialMapCenter"
      :zoom="20"
      :options="{
        zoomControl: true,
        mapTypeControl: true,
        scaleControl: false,
        streetViewControl: false,
        rotateControl: false,
        fullscreenControl: true,
        disableDefaultUi: false,
      }"
      @center_changed="updateCenter"
      @dblclick="showDropPin = true"
      style="width:100%;  height: 800px;"
    >
      <gmap-info-window
        :options="markerInfoOptions"
        :position="markerInfoWindowPos"
        :opened="markerInfoWinOpen"
        @closeclick="markerInfoWinOpen = false"
      />

      <!-- :icon="hazard.iconPath" -->
      <GmapMarker
        v-for="hazard in hazardsToDisplay"
        :key="hazard.hazardId"
        :position="hazard.address.coordinates"
        :clickable="true"
        @click="showMarkerInfoWindow(hazard, hazard.hazardId)"
      />
      <div class = "dropped-pin-container" v-if="showDropPin">
        <GmapMarker
          :position="currentMapCenter"
          :clickable="true"
          :draggable="true"
          @mouseover="updateDropPinLocation"
          @dragstart="droppedPinMarkerInfoWindow(false)"
          @dragend="updateDropPinLocation"
          @click="droppedPinMarkerInfoWindow(true)"
        />
      </div>
      
    </GmapMap>
  </div>
</template>

<script>
import dataService from "@/services/DataService.js";
// eslint-disable-next-line no-unused-vars
import geocodingService from "@/services/GeocodingService.js";
export default {
  name: "Map",
  emits: ["dropped-pin-hazard"],
  data() {
    return {
      hazards: [],
      initialMapCenter: { lat: 41.4993, lng: -81.6944 },
      currentMapCenter: { lat: 0, lng: 0},
      droppedPinLoc: {lat: 0, lng: 0},
      showDropPin: false,
      droppedPinAddress:{},
      markerInfoWindowPos: null,
      markerInfoWinOpen: false,
      currentMarkerId: null,
      markerInfoOptions: {
        content: "",
        pixelOffset: {
          width: 0,
          height: -35,
        },
      },
    };
  },
  props: {
    filteredHazards: Object,
    dropPin: Boolean
  },
  computed: {
    hazardsToDisplay() {
      if (this.filteredHazards == null) {
        return this.hazards;
      } else {
        return this.filteredHazards;
      }
    },
  },
  created(){window.emitDropPinPothole =this.emitDropPinPothole;},
  mounted() {
    this.geolocate();
    this.displayAllHazards();
  },
  methods: {
    displayAllHazards() {
      dataService
        .getAllHazards()
        .then((response) => (this.hazards = response.data));
    },
    geolocate() {
      this.currentMapCenter = this.initialMapCenter;
      navigator.geolocation.getCurrentPosition((position) => {
        this.mapCenter = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };
      });
    },
    updateCenter(latLng) {
      if(!this.showDropPin){
        this.currentMapCenter = {
            lat: latLng.lat(),
            lng: latLng.lng()
        }
      }
    },
    updateDropPinLocation(event){
      this.droppedPinLoc = event.latLng.toJSON();
    },
    showMarkerInfoWindow(hazard, id) {
      this.markerInfoWindowPos = hazard.address.coordinates;

      const infoWindowHazardText =
        hazard.hazardType +
        " at " +
        hazard.address.houseNumber +
        " " +
        hazard.address.streetName +
        " in " +
        hazard.address.city;
      const infoWindowTimestampText =
        "First reported on " +
        this.makeDatePretty(hazard.firstReportedTimestamp);
      const infoWindowLink = '<a href="/newreport">See more details</a>';
      const infoWindowText =
        "<p>" +
        infoWindowHazardText +
        "</p>" +
        "<p>" +
        infoWindowTimestampText +
        "</p>" +
        "<p>" +
        infoWindowLink +
        "</p>";

      this.markerInfoOptions.content = infoWindowText;

      if (this.currentMarkerId == id) {
        this.markerInfoWinOpen = !this.markerInfoWinOpen;
      } else {
        this.markerInfoWinOpen = true;
        this.currentMarkerId = id;
      }
    },
    droppedPinMarkerInfoWindow(show) {
      if(show){
        this.markerInfoWindowPos = this.droppedPinLoc;
        const potholeReportButton = '<input type="button" value="Report a Pothole Here" onclick="emitDropPinPothole()"/>'
        const drainReportButton = '<input type="button" value="Report a Drain Here" onclick="emitDropPinDrain()"/>'
        const infoWindowText = potholeReportButton + '<br>' + drainReportButton;
        this.markerInfoOptions.content = infoWindowText;
        this.markerInfoWinOpen = true;
      }
      else{
        this.markerInfoWinOpen = false;
      }
    },
    makeDatePretty(timestamp) {
      const date = timestamp.substring(0, 10);
      const time = timestamp.substring(11, 16);
      return date + " at " + time;
    },
    emitDropPinPothole(){
      this.droppedPinAddress = geocodingService.getFormattedAddress(this.droppedPinLoc);
      console.log(this.droppedPinAddress);
      // this.$emit("dropped-pin-hazard",this.droppedPinHazard);
    }
  },
  
};
</script>

<style></style>
