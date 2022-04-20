<template>
  <div class="home">
    <div id="sidebar">
      <h2><em>HELP SAVE OUR ROADS!</em></h2>
      <div class="brief">
        <p>
          <strong>HLHNTR</strong> is a road hazard reporting service for
          Northeast Ohio, including the Greater Cleveland and Akron metro areas.
        </p>
        <p>
          By crowd-sourcing data on common hazards to motorists and pedestrians
          alike, we are working to ensure that our roads are properly taken care
          of.
        </p>
      </div>
      <hazard-list
        v-bind:refreshData="emittedNewHazardId"
        @map-hazards="updateMap"
        @map-snap="snapMap"
      ></hazard-list>
      <div id="instructions">
        <p>
          To report a hazard, use the map to the right and double-click to
          select the location, <strong>OR</strong>, if you know the address -
          use our form provided below! <br />
        </p>
      </div>
    </div>
    <div id="mapbox">
      <Map
        v-bind:filteredHazards="mapHazards"
        @drop-pin-hazard="emitHazardFromMap"
        v-bind:snapLatLon="snapLatlon"
      />
    </div>

    <div class="report-hazard-container" ref="report">
      <new-report
        v-bind:propHazard="emittedMapHazard"
        @new-hazard-id="refreshPlease"
      />
    </div>
  </div>
</template>

<script>
import Map from "../components/Map.vue";
import HazardList from "@/components/HazardList.vue";
import NewReport from "../components/NewReport.vue";

export default {
  components: { Map, HazardList, NewReport },
  name: "home",
  data() {
    return {
      mapHazards: null,
      emittedMapHazard: null,
      emittedNewHazardId: null,
      snapLatlon: null,
    };
  },
  methods: {
    updateMap(hazards) {
      this.mapHazards = hazards;
    },
    emitHazardFromMap(hazard) {
      this.$refs["report"].scrollIntoView();
      this.emittedMapHazard = hazard;
    },
    refreshPlease(hazardId) {
      this.emittedNewHazardId = hazardId;
    },
    snapMap(latlon) {
      console.log("snap");
      this.snapLatlon = latlon;
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Kanit:ital,wght@0,100;0,400;0,900;1,100;1,400;1,900&display=swap");

.home {
  /* max-height: 100vh; */
  display: grid;
  /* align-content: center; */
  /* justify-content: center; */
  /* justify-items: center; */
  /* align-items: center; */
  grid-template-columns: 1fr 2fr;
  /* grid-template-rows: 3fr 1fr; */
  grid-template-areas:
    "sidebar mapbox"
    "sidebar reportbox";
  gap: 10px;
}

/* .sidebar-map-container {
  font-family: "Kanit", sans-serif;
} */

#instructions {
  background-color: #ccc;
  border-radius: 10px;
  color: #333;
  font-size: 13px;
  padding: 5px 20px;
  margin: 10px;
  font-weight: 400;
}

.brief {
  background-color: #ccc;
  border-radius: 10px;
  color: #111;
  font-size: 13px;
  padding: 15px;
  margin: 15px 10px;
  font-weight: 400;
}

#sidebar {
  margin: 0 15px;
  padding: 15px;
  background-color: #999;
  color: #000;
  grid-area: sidebar;
  border-radius: 0 0 20px 20px;
  overflow: auto;
  overflow-x: hidden;
  /* height: 450px; */
}
#mapbox {
  grid-area: mapbox;
  
}


.report-hazard-container {
  grid-area: reportbox;
  /* column-span: 2; */
  align-items: center;
  /* justify-content: center; */
  justify-self: center;
  margin: 20px 0 -30px 50px;
  width: 90%;
  /* height: 100px; */
}
h2 {
  color: black;
  text-shadow: 2px 2px fuchsia;
  font-size: 25px;
  margin: 5px 0;
}
</style>
