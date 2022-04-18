<template>
  <div class="manage-hazard-container">
     <h1 class="update-report-title-text">Update Hazard</h1>
    <form class="new-report-form" v-on:submit.prevent="saveNewReport">
      <label for="verified">Verified</label>
      <select id="verified" name="verified" v-model="updatedHazard.verified">
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

<div v-if="!isDrain">
      <label for="severity">Severity</label>
      <select id="severity" name="severity" v-model="updatedHazard.severity">
        <option value="low">Low: Quarter Sized (25mm or less)</option>
        <option value="moderate">Moderate: Lime Sized (25mm - 50mm)</option>
        <option value="high">High: Bigger Than A Lime (50mm or more)</option>
      </select>
</div>

<div v-if="isDrain">
      <label for="clogged">Clogged</label>
      <select id="clogged" name="clogged" v-model="updatedHazard.clogged">
        <option value="true">Yes</option>
        <option value="false">No</option>
      </select>
      </div>
       <!-- TODO combine date and time and format properly for inspect, schedule and
      repair "2022-04-16T14:29:42.371+00:00" "TimestampDate + T + TimestampTime + :00+00:00" -->
      
        <label for="inspected-timestamp">Time Inspected</label>
        <input type="date" id="inspectedTimestamp" name="inspectedTimestamp" />
        <input type="time" id="inspectedTimestampTime" name="inspectedTimestampTime" />
      

      
        <label for="scheduled-repair-timestamp">Scheduled Repair Time</label>
        <input type="date" id="scheduledRepairTimestamp" name="scheduledRepairTimestamp" />
        <input type="time" id="scheduledRepairTimestampTime" name="scheduledRepairTimestampTime" />
      

      
        <label for="repaired-timestamp">Repaired Time</label>
        <input type="date" id="repairedTimestamp" name="repairedTimestamp" />
        <input type="time" id="repairedTimestampTime" name="repairedTimestampTime" />
      

      <input type="Button" value="Update Report" />
      
    </form>
  </div>
</template>

<script>
export default {
  // TODO import edit data service
  name: "ManageHazard",
  props: ["propHazard"],
//mounted/created to populate data new method and call from mounted/created copy data from 
// update object structure copy prop onto it then update the rest
//new service to hit endpoint put request /hazardput
  data() {
    return {
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
            lng: ""
          }
        },
          hazardType: "",
          repairStatus: "",
          reportingUser: "",
          inspectedTimestamp: "",
          scheduledRepairTimestamp: "",
          repairedTimestamp: "",
          severity: "",
          clogged: ""
        }
    };
  },
  methods: {
    updateHazard() {

this.updatedHazard = this.propHazard;


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
  }
  },
  created() {
    this.updateHazard();
  }
  //created??
  //AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
  // methods: {
  //   updateReport() {
  //     switch (this.hazardType) {
  //       case "":
  //         alert("Hazard Type required in dropdown menu");
  //         break;
  //       case "POTHOLE":
  //         this.saveNewPotholeReport();
  //         break;
  //       case "DRAIN":
  //         this.saveNewDrainReport();
  //         break;
  //     }
  //   },
  //   saveNewPotholeReport() {
  //     this.hazard.address.streetName += " " + this.streetType;
  //     console.log(this.hazard);
  //     const config = {
  //       headers: { Authorization: `Bearer ${this.$store.state.token}` },
  //     };
  //     if (this.$store.user) {
  //       this.hazard.reportingUser = this.$store.state.user.id;
  //     }
  //     reportingService
  //       .addNewPotholeReport(this.hazard, config)
  //       .then((response) => {
  //         if (response.status == 201) {
  //           alert(
  //             "Pothole number: " +
  //               response.data.pothole.hazardId +
  //               " successfully entered. Thanks for reporting a pothole."
  //           );

  //           if (!this.$store.user) {
  //             this.$router.push({ name: "home" });
  //           } else {
  //             alert(
  //               "Ask anonymous user to register or allow to report another pothole"
  //             );
  //           }
  //           this.clearForm();
  //         }
  //       })
  //       .catch(() => {
  //         alert("Invalid address, please try again.");
  //       });
  //   },
  //   saveNewDrainReport() {
  //     this.hazard.address.streetName += " " + this.streetType;
  //     const config = {
  //       headers: { Authorization: `Bearer ${this.$store.state.token}` },
  //     };
  //     if (this.$store.user) {
  //       this.hazard.reportingUser = this.$store.state.user.id;
  //     }
  //     reportingService
  //       .addNewDrainReport(this.hazard, config)
  //       .then((response) => {
  //         if (response.status == 201) {
  //           alert(
  //             "Drain number: " +
  //               response.data.drain.hazardId +
  //               " successfully entered. Thanks for reporting a drain."
  //           );

  //           if (!this.$store.user) {
  //             this.$router.push({ name: "home" });
  //           } else {
  //             alert(
  //               "Ask anonymous user to register or allow to report another drain"
  //             );
  //           }
  //           this.clearForm();
  //         }
  //       })
  //       .catch(() => {
  //         alert("Invalid address, please try again.");
  //       });
  //   },
  //   clearForm() {
  //     this.hazard.address = {};
  //   },
  // },
  // computed: {
  //   isAddressFilledIn() {
  //     Object.values(this.hazard.address).forEach((element) => {
  //       console.log(element);
  //       if (element == "") {
  //         return false;
  //       }
  //     });
  //     return true;
  //   },
  // },
  // created() {
  //   this.streetTypes = require("street-types");
  // },
};

</script>

<style>
</style>

// Schedule for Inspection and repair - trello card
// Review reported hazards:
// edit:
// verified - dropdown (yes, no)
// repair status - dropdown (pending, scheduled, in progress, repaired)
// severity (for potholes) - dropdown (Low, Moderate, High) (show depth reference to known object)
// isClogged (for drains) - dropdown (yes, no) change to not clogged when repaired
// inspected time - use date/time picker and parse into proper format when sending to api
// scheduled repair time
// repaired time
// submit button - that will call a service method to be written
// https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_input_type_time
// https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_input_type_date