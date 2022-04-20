<template>
  <div>
    <GmapMap
      :center="initialMapCenter"
      :zoom="mapZoom"
      :options="{
        zoomControl: true,
        mapTypeControl: true,
        scaleControl: true,
        streetViewControl: false,
        rotateControl: false,
        fullscreenControl: true,
        disableDefaultUi: false,
        gestureHandling: 'greedy',
        disableDoubleClickZoom: true,
      }"
      @center_changed="updateCenter"
      @dblclick="dropPin"
      style="width:100%;  height: 800px;"
    >
      <gmap-info-window
        :options="markerInfoOptions"
        :position="markerInfoWindowPos"
        :opened="markerInfoWinOpen"
        @closeclick="markerInfoWinOpen = false"
      />
      <GmapMarker
        v-for="hazard in hazardsToDisplay"
        :key="hazard.hazardId"
        :position="hazard.address.coordinates"
        :clickable="true"
        @click="showMarkerInfoWindow(hazard, hazard.hazardId)"
        :icon="{ url: require('../img/mapicontiny.png') }"
      />
      <div class="drop-pin-container" v-if="showDropPin">
        <GmapMarker
          :position="droppedPinLoc"
          :clickable="true"
          :draggable="true"
          @mouseover="updateDropPinLocation"
          @dragstart="dropPinMarkerInfoWindow(false)"
          @dragend="updateDropPinLocation"
          @click="dropPinMarkerInfoWindow(true)"
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
  emits: ["drop-pin-hazard"],
  data() {
    return {
      hazards: [],
      mapZoom: 10,
      initialMapCenter: { lat: 41.4993, lng: -81.6944 },
      currentMapCenter: { lat: 0, lng: 0 },
      droppedPinLoc: { lat: 0, lng: 0 },
      showDropPin: false,
      droppedPinAddress: {},
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
    filteredHazards: Array,
    snapLatLon: {},
  },
  watch: {
    snapLatLon(old, newLoc) {
      console.log({ lat: newLoc.lat, lng: newLoc.lng });
      this.updateCenter(newLoc, true);
      this.mapZoom = 12;
    },
    currentMapCenter(old, newCenter) {
      console.log("old:" + old.lat);
      console.log("new:" + newCenter.lat);
    },
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
  created() {
    window.getAddressAtPin = this.getAddressAtPin;
    window.dropPin = this.dropPin;
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
      this.currentMapCenter = this.initialMapCenter;
      navigator.geolocation.getCurrentPosition((position) => {
        this.mapCenter = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };
      });
    },
    updateCenter(latLng, prop = false) {
      if (!this.showDropPin) {
        if (prop) {
          this.initialMapCenter = {
            lat: latLng.lat,
            lng: latLng.lng,
          };
        } else {
          this.currentMapCenter = {
            lat: latLng.lat(),
            lng: latLng.lng(),
          };
        }
      }
    },
    showMarkerInfoWindow(hazard, id) {
      this.markerInfoWindowPos = hazard.address.coordinates;
      let imgSrc = "";
      try {
        console.log("@/img/uploads/" + id + ".jpeg");
        imgSrc = require("@/img/uploads/" + id + ".jpeg");
        console.log("here");
        // do something
      } catch (e) {
        console.log("no image for this hazard");
      }
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

      const infoWindowLink = `<img style = "max-width:200px; height:auto" src="${imgSrc}" />`;
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
    dropPin(event) {
      if (this.showDropPin) {
        this.showDropPin = false;
      } else {
        this.droppedPinLoc = {
          lat: event.latLng.lat(),
          lng: event.latLng.lng(),
        };
        this.showDropPin = true;
      }
    },
    updateDropPinLocation(event) {
      this.droppedPinLoc = event.latLng.toJSON();
    },
    dropPinMarkerInfoWindow(show) {
      if (show) {
        this.markerInfoWindowPos = this.droppedPinLoc;

        const potholeReportFunction = "getAddressAtPin('POTHOLE')";
        const drainReportFunction = "getAddressAtPin('DRAIN')";

        const potholeReportButton = `<input type="button" value="Report a Pothole Here" onclick="${potholeReportFunction}"/>`;
        const drainReportButton = `<input type="button" value="Report a Drain Here" onclick="${drainReportFunction}"/>`;
        const infoWindowText = potholeReportButton + "<br>" + drainReportButton;
        this.markerInfoOptions.content = infoWindowText;
        this.markerInfoWinOpen = true;
      } else {
        this.markerInfoWinOpen = false;
      }
    },
    getAddressAtPin(hazardType) {
      this.droppedPinAddress = null;
      geocodingService
        .reverseGeocode(this.droppedPinLoc)
        .then((response) => {
          this.droppedPinAddress = response.data.results[0].formatted_address;

          const splitAddress = this.droppedPinAddress.split(",");
          const number = splitAddress[0].split(" ", 1)[0];
          const street = splitAddress[0].replace(number, "").trim();
          const city = splitAddress[1].trim();
          const state = splitAddress[2].trim().split(" ")[0];
          const zip = splitAddress[2].trim().split(" ")[1];

          const emitOutput = {
            hazardType: hazardType,
            address: {
              houseNumber: number,
              streetName: street,
              city: city,
              state: state,
              zip: zip,
            },
          };
          this.$emit("drop-pin-hazard", emitOutput);
        })
        .catch((error) => {
          console.log(error);
          alert("no address found");
        });
    },
    makeDatePretty(timestamp) {
      const date = timestamp.substring(0, 10);
      const time = timestamp.substring(11, 16);
      return date + " at " + time;
    },
  },
};
</script>

<style>

// hazardType: "", // streetType: "", // hazard: { // verified: false, //
address: { // houseNumber: "", // streetName: "", // city: "", // state: "", //
zip: "", // }, // repairStatus: "pending", // reportingUser: 1, // severity: "",
// },
