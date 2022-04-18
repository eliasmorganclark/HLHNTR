<template>
  <div class="home">
    <div class="sidebar-map-container">
      <!-- <h1>Home</h1>
      <p>Map placeholder</p> -->
      <div id="sidebar">
        <h2>HELP SAVE NORTHEAST OHIO</h2>
        <hazard-list @map-hazards="updateMap"></hazard-list>
      </div>
      <div id="mapbox">
        <Map v-bind:filteredHazards="mapHazards" v-bind:dropPin="dropPin" />
      </div>
    </div>
    <div class="report-hazard-container">
      <new-report/>
    </div>
  </div>
</template>

<script>
import Map from "../components/Map.vue";
import HazardList from "@/components/HazardList.vue";
import NewReport from '../components/NewReport.vue';

export default {
  components: { Map, HazardList, NewReport },
  name: "home",
  data() {
    return {
      mapHazards: null,
      dropPin: false
    };
  },
  methods: {
    updateMap(hazards) {
      this.mapHazards = hazards;
    },
    enableDropPin(flag){
      this.dropPin = flag;
    }
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Kanit:ital,wght@0,100;0,400;0,900;1,100;1,400;1,900&display=swap");

.sidebar-map-container {
  font-family: "Kanit", sans-serif;
  display: flex;
  flex-direction: row;
}
#sidebar {
  max-width: 500px;
  border-left: 10px solid black;
  border-right: 10px solid black;
  padding: 20px;
  background-color: #999;
  color: #000;

  flex-grow: 1;
}
#mapbox {
  flex-grow: 5;
}
h2 {
  font-size: 30px;
}
</style>
