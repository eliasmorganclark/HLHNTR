<template>
<div class="hazard-details-container" >
  <div class="hazard-info-container">
    <h1 class='hazard-title'>{{hazard.hazardType}} detail info:</h1>
    <h2 class='hazard-id'>Hazard ID: {{hazard.hazardId}}</h2>
    <h2 class='address'>Address: {{hazard.address.houseNumber}} {{hazard.address.streetName}} {{hazard.address.city}} {{hazard.address.state}} {{hazard.address.zip}}</h2>
    <h2 class='latitude'>Latitude: {{hazard.address.coordinates.lat}}</h2>
    <h2 class='longitude'>Longitude: {{hazard.address.coordinates.lng}}</h2>
    <h2 class='verified'>Verified: {{hazard.verified}}</h2>
    <h2 class='repair-status'>Repair Status: {{hazard.repairStatus}}</h2>
    <h2 v-if="!isDrain" class='severity'>Severity: {{hazard.severity}}</h2>
    <h2 v-if="isDrain" class='is-clogged'>Clogged?: {{hazard.clogged}}</h2>
    <h2 class='timeFirstReported'>First reported at: {{makeDatePretty(hazard.firstReportedTimestamp)}}</h2>  
  </div> 
  <div class="details-report-container" v-if="doneLoading" >
    <h1 class='reports-title'>Reports:</h1>
    <h2 class="reports" v-for="report in reports" v-bind:key="report.reportId" >Report: {{report.reportId}} reported by User: {{report.reportingUser}} on: {{makeDatePretty(report.reportedTimestamp)}}</h2>
  </div>
  <div class="details-times-container">
    <h1 class='reports-title'>Inspection and Repair Scheduling:</h1>
    <h2 class='timeFirstReported'>Scheduled for inspection: {{makeDatePretty(hazard.inspectedTimestamp)}}</h2>
    <h2 class='timeFirstReported'>Scheduled for repair: {{makeDatePretty(hazard.scheduledRepairTimestamp)}}</h2>
    <h2 class='timeFirstReported'>Repair completed: {{makeDatePretty(hazard.repairedTimestamp)}}</h2>
  </div>
  <div class="details-image-container" v-if="imageExists">
    <img :src="imgSrc" />
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
        doneLoading: false,
        isDrain: false,
        imgSrc: '',
        imageExists:false
      }
    },
    created(){
        dataService.getReportsForHazard(this.hazard.hazardId).then(response => {
          this.reports = response.data;
          this.doneLoading = true;
          if(this.hazard.hazardType=='DRAIN'){
            this.isDrain = true;
          }
        }),
        this.getImage()
    },
    methods:{
      makeDatePretty(timestamp){
        if(timestamp){
          const date = timestamp.substring(0,10);
          const time = timestamp.substring(11,16);
          return date + ' at ' + time;
        }
        else{
          return "";
        }
    },
    getImage(){
      try {
        console.log('@/img/uploads/'+ this.hazard.hazardId + ".jpeg");
         this.imgSrc = require('@/img/uploads/'+ this.hazard.hazardId + ".jpeg");
         this.imageExists = true;
        // do something
      } catch (e) {
        console.log("no image for this hazard")
      }
    }

}
    
}

</script>

<style>
  .hazard-details-container{
    display: flex;
    justify-content: space-evenly;
  }
</style>


// try {
//   this.storyToRead = require('../assets/data/stories/' + this.storyFileName)
//   // do something
// } catch (e) {
//   // do something else
// }