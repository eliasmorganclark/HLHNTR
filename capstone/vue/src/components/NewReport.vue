<template>
  <div class="new-report-container">
    <div class="new-report-title-container">
      <h1 class="new-report-title-text">Report a Hazard</h1>
      <h2 class="new-report-title-text">
        Please enter the address of the hazard below:
      </h2>
    </div>
    <div class="new-report-form-container">

      <form class="new-report-form" v-on:submit.prevent="saveNewReport">
        <label for="hazard-type">Hazard Type</label>
        <select v-model="hazardType" name="hazard-type" id="hazard-type">
          <option value="">------</option>
          <option value="POTHOLE">Pothole</option>
          <option value="DRAIN">Drain</option>
        </select>


        <label for="house-number">House Number</label>
        <input
          v-model.trim="hazard.address.houseNumber"
          id="houser-number"
          type="text"
        />

        <label for="street-name">Street Name</label>
        <input
          v-model.trim="hazard.address.streetName"
          id="street-name"
          type="text"
        />

        <label for="city">City</label>
        <input v-model.trim="hazard.address.city" id="city" type="text" />

        <label for="state">State</label>
        <input v-model.trim="hazard.address.state" id="state" type="text" />

        <label for="zip-code">Zip Code</label>
        <input v-model.trim="hazard.address.zip" id="zip-code" type="text" />
        <input type="submit" value="Submit Report" />
        <input
          type="button"
          v-on:click.prevent="clearForm"
          value="Clear Form"
        />
      </form>
    </div>
  </div>

  <!-- <address-form v-model="report.pothole.address"></address-form> -->
</template>

<script>
import reportingService from "@/services/ReportingService.js";
// import AddressForm from "./AddressForm.vue";

export default {
  name: "new-report",
  // prop goes here for communication with AddressForm???
  // components: {
  //   AddressForm,
  // },
  data() {
    return {
      hazardType: "",
      hazard: {
        verified: false,
        address: {
          houseNumber: "",
          streetName: "",
          city: "",
          state: "",
          zip: "",
        },
        repairStatus: "pending",
        reportingUser: 1,
      },
    };
  },
  methods: {
    saveNewReport() {
      switch (this.hazardType) {
        case "":
          alert("Hazard Type required in dropdown menu");
          break;
        case "POTHOLE":
          this.saveNewPotholeReport();
          break;
        case "DRAIN":
          this.saveNewDrainReport();
          break;
      }
    },
    saveNewPotholeReport() {
      const config = {
        headers: { Authorization: `Bearer ${this.$store.state.token}` },
      };
      if (this.$store.user) {
        this.hazard.reportingUser = this.$store.state.user.id;
      }
      reportingService
        .addNewPotholeReport(this.hazard, config)
        .then((response) => {
          if (response.status == 201) {
            alert(
              "Pothole number: " +
                response.data.pothole.hazardId +
                " successfully entered. Thanks for reporting a pothole."
            );

            if (!this.$store.user) {
              this.$router.push({ name: "home" });
            } else {
              alert(
                "Ask anonymous user to register or allow to report another pothole"
              );
            }
            this.clearForm();
          }
        })
        .catch(() => {
          alert("Invalid address, please try again.");
        });
    },
    saveNewDrainReport() {
      const config = {
        headers: { Authorization: `Bearer ${this.$store.state.token}` },
      };
      if (this.$store.user) {
        this.hazard.reportingUser = this.$store.state.user.id;
      }
      reportingService
        .addNewDrainReport(this.hazard, config)
        .then((response) => {
          if (response.status == 201) {
            alert(
              "Drain number: " +
                response.data.drain.hazardId +
                " successfully entered. Thanks for reporting a drain."
            );

            if (!this.$store.user) {
              this.$router.push({ name: "home" });
            } else {
              alert(
                "Ask anonymous user to register or allow to report another drain"
              );
            }
            this.clearForm();
          }
        })
        .catch(() => {
          alert("Invalid address, please try again.");
        });
    },
    clearForm() {
      this.hazard.address = {};
    },
  },
  computed: {
    isAddressFilledIn() {
      Object.values(this.hazard.address).forEach((element) => {
        console.log(element);
        if (element == "") {
          return false;
        }
      });
      return true;
    },
  },
};
</script>

<style></style>
