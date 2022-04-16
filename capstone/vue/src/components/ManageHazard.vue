<template>
  <div class="manage-hazard-container">
    <form class="new-report-form" v-on:submit.prevent="saveNewReport">

      <label for="verified">Verified</label>
      <select id="verified" name="verified" v-model="hazard.verified">
        <option value="true">Yes</option>
        <option value="false">No</option>
      </select>

      <label for="repair-status">Repair Status</label>
      <select id="repair-status" name="repair-status" v-model="hazard.repairStatus" >
        <option value="pending">Pending</option>
        <option value="scheduled">Scheduled</option>
        <option value="inProgress">In Progress</option>
        <option value="repaired">Repaired</option>
      </select>

      <label for="severity">Severity</label>
      <select id="severity" name="severity" v-model="hazard.severity">
        <option value="low">Quarter Sized (25mm or less)</option>
        <option value="moderate">Lime Sized (25mm - 50mm)</option>
        <option value="high">Bigger Than A Lime (50mm or more)</option>
      </select>

      <label for="clogged">Clogged</label>
      <select id="clogged" name="clogged" v-model="hazard.clogged">
        <option value="true">Yes</option>
        <option value="false">No</option>
      </select>

      <form action="/action_page.php">
        <label for="inspected-timestamp">Time Inspected</label>
        <input type="date" id="inspectedTimestamp" name="inspectedTimestamp" />
        <input type="time" id="inspectedTimestamp" name="inspectedTimestamp" />
      </form>

      <form action="/action_page.php">
        <label for="scheduled-repair-timestamp">Scheduled Repair Time</label>
        <input type="date" id="inspectedTimestamp" name="inspectedTimestamp" />
        <input type="time" id="inspectedTimestamp" name="inspectedTimestamp" />
      </form>

      <form action="/action_page.php">
        <label for="repaired-timestamp">Repaired Time</label>
        <input type="date" id="inspectedTimestamp" name="inspectedTimestamp" />
        <input type="time" id="inspectedTimestamp" name="inspectedTimestamp" />
      </form>

      <input type="Update" value="Update Report" />
      <input type="button" v-on:click.prevent="clearForm" value="Clear Form" />
    </form>
  </div>
</template>

<script>
export default {
  // TODO import edit data service
  name: "ManageHazard",
  props: ["hazard"],

data(){
      return{
        hazard: {
          verified: "",
          repairStatus: "",
          severity: "",
          clogged: "",
          inspectedTimestamp: "",
          scheduledRepairTimestamp: "",
          repairedTimestamp: ""
        }
      }
    },
    created(){
        
            },
    methods:{
      makeDatePretty(timestamp){
        const date = timestamp.substring(0,10);
        const time = timestamp.substring(11,16);
        return date + ' at ' + time;
    }
}
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