<template>
<div>
<hazard-details v-bind:hazard="this.$store.state.potholes[0]" /> 
</div>
</template>

<script>
import HazardDetails from '../components/HazardDetails.vue'
export default {
  components: { HazardDetails },

}
</script>

<style>

</style>