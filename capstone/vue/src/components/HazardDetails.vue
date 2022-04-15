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
<h2 class="reports" v-for="report in this.getReportsByHazard(hazard.hazardId, hazard.hazardType)" v-bind:key="report.reportId" >Reports: {{report}}</h2>
</div>
</template>

<script>


export default {
    name: 'HazardDetails',

    props: ['hazard'], 

    methods: {
        getReportsByHazard (hazardId, hazardType) {
      return this.$store.state.reports.filter((report) => {
          if (hazardType=='POTHOLE') {
        return ((report.pothole.hazardId==hazardId));
          } else if (hazardType == 'DRAIN') {
              return ((report.drain.hazardId==hazardId));
          } 
          return false;
      })
    }
}
}

</script>

<style>

</style>