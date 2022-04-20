<template>
  <div class="manage-hazard-container">
    <div class="update-hazard-form-container">
      <h1 class="update-report-title-text">UPDATE HAZARD</h1>
      <form class="new-report-form" v-on:submit.prevent="saveNewReport">
        <div class="form-dropdown-options">
          <label for="verified">Verified</label>
          <select
            id="verified"
            name="verified"
            v-model="updatedHazard.verified"
          >
            <option value="true">Yes</option>
            <option value="false">No</option>
          </select>

          <label for="repair-status">Repair Status</label>
          <select
            id="repair-status"
            name="repair-status"
            v-model="updatedHazard.repairStatus"
          >
            <option value="pending">Pending</option>
            <option value="scheduled">Scheduled</option>
            <option value="inProgress">In Progress</option>
            <option value="repaired">Repaired</option>
          </select>

          <span v-if="!isDrain">
            <label for="severity">Severity</label>
            <select
              id="severity"
              name="severity"
              v-model="updatedHazard.severity"
            >
              <option value="low">Low: Quarter Sized (25mm or less)</option>
              <option value="moderate"
                >Moderate: Lime Sized (25mm - 50mm)</option
              >
              <option value="high"
                >High: Bigger Than A Lime (50mm or more)</option
              >
            </select>
          </span>

          <span v-if="isDrain">
            <label for="clogged">Clogged</label>
            <select id="clogged" name="clogged" v-model="updatedHazard.clogged">
              <option value="true">Yes</option>
              <option value="false">No</option>
            </select>
          </span>
        </div>
        <!-- TODO combine date and time and format properly for inspect, schedule and
      repair "2022-04-16T14:29:42.371+00:00" "TimestampDate + T + TimestampTime + :00+00:00" -->

        <label for="inspected-timestamp">Time Inspected</label>
        <input
          type="date"
          id="inspectedTimestamp"
          name="inspectedTimestamp"
          v-model="updatedHazard.inspectedDT.fullDate"
        />
        <input
          type="time"
          id="inspectedTimestampTime"
          name="inspectedTimestampTime"
          v-model="updatedHazard.inspectedDT.fullTime"
        />
<BR />
        <label for="scheduled-repair-timestamp">Scheduled Repair Time</label>
        <input
          type="date"
          id="scheduledRepairTimestamp"
          name="scheduledRepairTimestamp"
          v-model="updatedHazard.scheduledRepairDT.fullDate"
        />
        <input
          type="time"
          id="scheduledRepairTimestampTime"
          name="scheduledRepairTimestampTime"
          v-model="updatedHazard.scheduledRepairDT.fullTime"
        />
<BR />
        <label for="repaired-timestamp">Repaired Time</label>
        <input
          type="date"
          id="repairedTimestamp"
          name="repairedTimestamp"
          v-model="updatedHazard.repairedDT.fullDate"
        />
        <input
          type="time"
          id="repairedTimestampTime"
          name="repairedTimestampTime"
          v-model="updatedHazard.repairedDT.fullTime"
        />
<BR />
        <input
          type="Button"
          value="Update Report"
          v-on:click="sendUpdatedHazard"
        />

        <!-- Show delete hazard if user-->
        
      </form>
    </div>
    <div
          v-if="this.$store.state.user.authorities[0].name == 'ROLE_ADMIN'"
          class="delete-hazard-container"
        >
          <!-- Currently passes in propHazardId, hazard of current page -->
          <form v-on:submit="deleteHazard(propHazard.hazardId)">
            <label for="delete-hazard"><h1>DELETE HAZARD</h1></label>
            <!-- <input v-model="hazardIdToDelete" id="delete-hazard" type="text" /> -->
            <input type="submit" value="Delete Current Hazard" />
          </form>
        </div>
  </div>
</template>

<script>
import dataService from "@/services/DataService.js";
export default {
  // TODO import edit data service
  name: "ManageHazard",
  props: ["propHazard"],
  //mounted/created to populate data new method and call from mounted/created copy data from
  // update object structure copy prop onto it then update the rest
  //new service to hit endpoint put request /hazardput
  data() {
    return {
      hazardIdToDelete: this.propHazard.hazardId,
      isDrain: Boolean,
      updatedHazard: {
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
        inspectedTimestamp: "",
        scheduledRepairTimestamp: "",
        repairedTimestamp: "",
        severity: "",
        clogged: "",
      },
    };
  },
  methods: {
    deleteHazard() {
      dataService
          .deleteHazard(this.propHazard.hazardId)
          .then(() => {
            alert("Pothole deleted");
            
          })
          this.$router.push({ name: "home" });
          
      // Add code to delete hazard by id here
      // This is so code still compiles
      // Route back to home page
    },
    updateHazard() {
      this.updatedHazard = this.propHazard;
      if (this.updatedHazard.hazardType == "DRAIN") {
        this.isDrain = true;
      } else {
        this.isDrain = false;
      }

      this.updatedHazard.hazardType = this.propHazard.hazardType;
      this.updatedHazard.verified = this.propHazard.verified;
      //all of the other data from the prop?
      this.updatedHazard.hazardId = this.propHazard.hazardId;
      this.updatedHazard.address = this.propHazard.address;

      this.updatedHazard.repairStatus = this.propHazard.repairStatus;
      //severity, clogged
      if (this.propHazard.clogged) {
        this.updatedHazard.clogged = this.propHazard.clogged;
      }
      if (this.propHazard.severity) {
        this.updatedHazard.severity = this.propHazard.severity;
      }
      //time things
      // this.updatedHazard.inspectedTimestamp += "T" + this.inspectedTimestampTime + ":00";
      // this.updatedHazard.scheduledRepairTimestamp += "T" + this.scheduledRepairTimestampTime + ":00";
      // this.updatedHazard.repairedTimestamp += "T" + this.repairedTimestampTime + ":00";
    },
    sendUpdatedHazard() {
      if (this.updatedHazard.hazardType == "POTHOLE") {
        dataService
          .updatePothole(this.updatedHazard)
          .then(() => {
            alert("Pothole updated");
          })
          .catch(() => {
            alert("Error updating pothole");
          });
      } else if (this.updatedHazard.hazardType == "DRAIN") {
        dataService
          .updateDrain(this.updatedHazard)
          .then(() => {
            alert("Drain updated");
          })
          .catch(() => {
            alert("Error updating drain");
          });
      }
    },
  },
  created() {
    this.updateHazard();
  },

  //AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
};
</script>

<style>

input {
  width: 150px;
}
.manage-hazard-container {
  display: flex;
  justify-content: space-AROUND;
  background-color: #ccc;
  padding: 20px;
  margin: 20px;
  border-radius: 10px;
}
</style>

// Schedule for Inspection and repair - trello card // Review reported hazards:
// edit: // verified - dropdown (yes, no) // repair status - dropdown (pending,
scheduled, in progress, repaired) // severity (for potholes) - dropdown (Low,
Moderate, High) (show depth reference to known object) // isClogged (for drains)
- dropdown (yes, no) change to not clogged when repaired // inspected time - use
date/time picker and parse into proper format when sending to api // scheduled
repair time // repaired time // submit button - that will call a service method
to be written //
https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_input_type_time //
https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_input_type_date
