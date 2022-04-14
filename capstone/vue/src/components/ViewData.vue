<template>
  <div>
    <div
      class="report-container"
      v-for="report in reports"
      v-bind:key="report.reportId"
    >
      <p>Report id: {{ report.reportId }}</p>
      <p>Report address: {{ report.pothole.address }}</p>
    </div>
  </div>
</template>

<script>
import dataService from "@/services/DataService.js";

export default {
  data() {
    return {
      reports: [],
    };
  },
  created() {
    dataService.getAllReports().then((response) => {
      console.log(response.data);
      this.reports = response.data;
    });
  },
};
</script>

<style></style>
