<template>
  <div>
    <GmapMap
      :center="initialMapCenter"
      :zoom="12"
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
      
    </GmapMap>
  </div>
</template>

<script>
import dataService from "@/services/DataService.js";
export default {
  name: "Map",
  data() {
    return {
      hazards: [],
      initialMapCenter: { lat: 41.4993, lng: -81.6944 },
      currentMapCenter: { lat: 0, lng: 0},
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
      navigator.geolocation.getCurrentPosition((position) => {
        this.mapCenter = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };
      });
    },
    updateCenter(latLng) {
      this.currentMapCenter = {
          lat: latLng.lat(),
          lng: latLng.lng()
      }
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
    makeDatePretty(timestamp) {
      const date = timestamp.substring(0, 10);
      const time = timestamp.substring(11, 16);
      return date + " at " + time;
    },
  },
  
};
</script>

<style></style>
