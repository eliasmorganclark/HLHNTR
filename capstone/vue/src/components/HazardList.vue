<template>
  <div class="hazard-list-container">
    <!-- Control Buttons -->
    <label for="hazard-filter">Hazard Filter</label>
    <select
      v-model="hazardFilter"
      name="hazard-filter"
      id="hazard-filter"
      v-on="displayHazards(hazardFilter)"
    >
      <option value="">All</option>
      <option value="POTHOLES">Potholes</option>
      <option value="DRAINS">Drains</option>
    </select>
    <div
      class="hazard-list"
      v-for="hazard in filteredList"
      :key="hazard.hazardId"
    >
      {{ hazard }}
    </div>
  </div>
</template>

<script>
export default {
  name: "hazard-list",
  data() {
    return {
      hazardFilter: "",
      filteredList: [],
    };
  },
  methods: {
    displayAllHazards() {
      this.filteredList = this.$store.getters.getAllHazards;
      // Target function looks like this
      //this.filteredList = this.$store.getters.getfilteredHazards(filter function here);
    },
    displayAllPotholes() {
      this.filteredList = this.$store.getters.getAllHazards.filter(
        (element) => element.hazardType == "POTHOLE"
      );
    },
    displayAllDrains() {
      this.filteredList = this.$store.getters.getAllHazards.filter(
        (element) => element.hazardType == "DRAIN"
      );
    },
    displayHazards(value) {
      switch (value) {
        case "":
          this.displayAllHazards();
          break;
        case "POTHOLES":
          this.displayAllPotholes();
          break;
        case "DRAINS":
          this.displayAllDrains();
          break;
      }
    },
  },
  created() {
    this.displayHazards();
  },
};
</script>

<style></style>
