<template>
  <div class="manage-hazard-container">

  </div>
</template>

<script>


export default {
    name: 'ManageHazard',
    props: ['hazard'],

}
</script>

<style>

</style>

//Schedule for Inspection and repair - trello card
//Review reported hazards:
//edit:
//verified - dropdown (yes, no)
//repair status - dropdown (pending, scheduled, in progress, repaired)
//severity (for potholes) - dropdown (Low, Moderate, High) (show depth reference to known object)
//isClogged (for drains) - dropdown (yes, no) change to not clogged when repaired
//inspected time - use date/time picker and parse into proper format when sending to api
//scheduled repair time
//repaired time
//submit button - that will call a service method to be written
//https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_input_type_time
//https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_input_type_date