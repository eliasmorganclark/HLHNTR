<template>
<div class="hazard-details" >
  <h2 class='hazard-id'>Hazard ID: {{hazard.hazardId}}</h2>
  <h2 class='address'>Address: {{hazard.address.houseNumber}} {{hazard.address.streetName}} {{hazard.address.city}} {{hazard.address.state}} {{hazard.address.zip}}</h2>
  <h2 class='latitude'>Latitude: {{hazard.address.coordinates.lat}}</h2>
  <h2 class='longitude'>Longitude: {{hazard.address.coordinates.lng}}</h2>
  <h2 class='verified'>Verified: {{hazard.verified}}</h2>
  <h2 class='repair-status'>Repair Status: {{hazard.repairStatus}}</h2>
  <h2 v-if="hazard.severity" class='severity'>Severity: {{hazard.severity}}</h2>
  <h2 v-if="hazard.isClogged" class='is-clogged'>Clogged?: {{hazard.isClogged}}</h2>   
  <div class="details-report-container" v-if="doneLoading" >
    <h2 class="reports" v-for="report in reports" v-bind:key="report.reportId" >Report: {{report.reportId}} reported by User: {{report.reportingUser}}</h2>
  </div>
</div>
</template>

<script>
import dataService from "@/services/DataService.js";
export default {
    name: 'HazardDetails',

    props: ['hazard'], 
    data(){
      return{
        reports:[], 
        doneLoading: false
      }
    },
    created(){
        dataService.getReportsForHazard(this.hazard.hazardId).then(response => {
          this.reports = response.data;
          this.doneLoading = true;
        })
    }
    
}

</script>

<style>

</style>