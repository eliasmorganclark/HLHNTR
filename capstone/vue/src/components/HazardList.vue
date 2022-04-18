<template>
  <div class="hazard-list-container">
    <!-- Control Buttons -->
    <h2>Hazard Filter List</h2>
    <label for="hazard-filter">Hazard Filter</label>
    <select
      v-model="filter.hazardType"
      name="hazard-filter"
      id="hazard-filter"
      @change="$emit('map-hazards', filteredHazards)"
    >
      <option value="">All</option>
      <option value="POTHOLE">Potholes</option>
      <option value="DRAIN">Drains</option>
    </select>
    <ul>
    <li
      class="hazard-list"
      v-for="hazard in filteredHazards"
      :key="hazard.hazardId"
    >
      <strong>{{ hazard.hazardType }}</strong> - {{ hazard.address.houseNumber }} {{hazard.address.streetName }},<em> {{hazard.address.city}} , 
      {{hazard.address.state}}</em> - {{hazard.address.zip}}
    </li>
    </ul>
  </div>
</template>

<script>
import dataService from "@/services/DataService.js";

export default {
  name: "hazard-list",
  emits: ["map-hazards"],
  data() {
    return {
      filter: {
        hazardId: "",
        verified: "",
        address: {
          houseNumber: "",
          streetName: "",
          city: "",
          state: "",
          zip: "",
          coordinates: {
            lat: "",
            lng: "",
          },
        },
        hazardType: "",
        repairStatus: "",
        reportingUser: "",
        firstReportedTimestamp: "",
        inspectedTimestamp: "",
        scheduledRepairTimestamp: "",
        repairedTimestamp: "",
        severity: "",
        clogged: "",
      },
      hazards: [],
    };
  },
  computed: {
    filteredHazards() {
      // Filter object is a model which includes all attributes of any hazard
      // includes both drain and pothole fields
      //
      //   {
      // "hazardId": 1001, done
      // "verified": false,
      // "address": {
      //     "houseNumber": 1234,
      //     "streetName": "HOLE CIRCLE",
      //     "city": "AKRON",
      //     "state": "OH",
      //     "zip": 44444,
      //     "coordinates": null
      // },
      // "hazardType": "DRAIN",
      // "repairStatus": "Not repaired",
      // "reportingUser": null,
      // "clogged": true
      // "severity": "",
      //     // INSERT DATES HERE
      //     // INSERT FIELDS HERE
      // }
      let filteredHazards = this.hazards;
      // Check filter
      if (this.filter.hazardId != "") {
        filteredHazards = filteredHazards.filter(
          (element) => element.hazardId == this.filter.hazardId
        );
      }

      if (this.filter.verified != "") {
        filteredHazards = filteredHazards.filter(
          (element) => element.verified == this.filter.verified
        );
      }
      if (this.filter.address != "") {
        //         "houseNumber": 1234,
        if (this.filter.address.houseNumber != "") {
          filteredHazards = filteredHazards.filter(
            (element) =>
              element.address.houseNumber == this.filter.address.houseNumber
          );
        }
        //         "streetName": "HOLE CIRCLE",
        if (this.filter.address.streetName != "") {
          filteredHazards = filteredHazards.filter(
            (element) =>
              element.address.streetName == this.filter.address.streetName
          );
        }
        //         "city": "AKRON",
        if (this.filter.address.city != "") {
          filteredHazards = filteredHazards.filter(
            (element) => element.address.city == this.filter.address.city
          );
        }
        //         "state": "OH",
        if (this.filter.address.state != "") {
          filteredHazards = filteredHazards.filter(
            (element) => element.address.state == this.filter.address.state
          );
        }
        //         "zip": 44444,
        if (this.filter.address.zip != "") {
          filteredHazards = filteredHazards.filter(
            (element) => element.address.zip == this.filter.address.zip
          );
        }
        //         "coordinates": null
        if (this.filter.address.coordinates != "") {
          if (this.filter.address.coordinates.lat) {
            filteredHazards = filteredHazards.filter(
              (element) =>
                element.address.coordinates.lat ==
                this.filter.address.coordinates.lat
            );
          }
          if (this.filter.address.coordinates.lng != "") {
            filteredHazards = filteredHazards.filter(
              (element) =>
                element.address.coordinates.lng ==
                this.filter.address.coordinates.lng
            );
          }
        }
      }

      if (this.filter.hazardType != "") {
        filteredHazards = filteredHazards.filter(
          (element) => element.hazardType == this.filter.hazardType
        );
      }

      if (this.filter.reportingUser != "") {
        filteredHazards = filteredHazards.filter(
          (element) => element.reportingUser == this.filter.reportingUser
        );
      }

      if (this.filter.clogged != "") {
        filteredHazards = filteredHazards.filter(
          (element) => element.clogged == this.filter.clogged
        );
      }

      if (this.filter.severity != "") {
        filteredHazards = filteredHazards.filter(
          (element) => element.severity == this.filter.severity
        );
      }
      return filteredHazards;
    },
  },
  created() {
    this.displayAllHazards();
  },
  methods: {
    displayAllHazards() {
      dataService
        .getAllHazards()
        .then((response) => (this.hazards = response.data));
    },
  },
};
</script>

<style></style>
