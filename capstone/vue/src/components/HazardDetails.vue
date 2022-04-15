<template>
<div class="hazard-details" >
<h2 class='hazard-id'> {{hazard.hazardId}}<h2>
<h2 class='house-number'> {{hazard.houseNumber}}<h2>
<h2 class='street-name'> {{hazard.streetName}}<h2>
<h2 class='city'> {{hazard.city}}<h2>
<h2 class='state'> {{hazard.state}}<h2>
<h2 class='zip'> {{hazard.zip}}<h2>
<h2 class='latitude'> {{hazard.latitude}}<h2>
<h2 class='longitude'> {{hazard.longitude}}<h2>
<h2 class='verified'> {{hazard.verified}}<h2>
<h2 class='repair-status'> {{hazard.repairStatus}}<h2>
<h2 v-if="hazard.severity" class='severity'> {{hazard.severity}}<h2>
<h2 v-if="hazard.isClogged" class='is-clogged'> {{hazard.isClogged}}<h2>   
</div>
</template>

<script>


export default {
    name: 'HazardDetails',
    props: 'hazard', 
}

</script>

<style></style>