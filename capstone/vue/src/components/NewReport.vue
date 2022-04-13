<template>
  <div class="new-report-container">
    <div class="new-report-title-container">
      <h1 class="new-report-title-text">Report a pothole</h1>
      <h2 class="new-report-title-text">Please enter the address of the pothole below:</h2>
    </div>
    <div class="new-report-form-container">
      <form class="new-report-form" v-on:submit.prevent="saveReport">
        <label for="house-number">House Number</label>
        <input
          v-model="report.pothole.address.houseNumber"
          id="houser-number"
          type="text"
        />

        <label for="street-name">Street Name</label>
        <input
          v-model="report.pothole.address.streetName"
          id="street-name"
          type="text"
        />

        <label for="city">City</label>
        <input v-model="report.pothole.address.city" id="city" type="text" />

        <label for="state">State</label>
        <input v-model="report.pothole.address.state" id="state" type="text" />

        <label for="zip-code">Zip Code</label>
        <input v-model="report.pothole.address.zip" id="zip-code" type="text" />
        <br>
        <input type="submit" value="Submit Report" />
        <input type="button" v-on:click.prevent="clearForm" value="Clear Form" />
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
      report: {
        reportingUser: 1,
        pothole: {
          verified: false,
          repairStatus: "not repaired",
          severity: "bad",
          address: {
            houseNumber: "",
            streetName: "",
            city: "",
            state: "",
            zip: "",
          },
        },
      },
    };
  },
  methods: {
    saveReport() {
      const config = {
        headers: { Authorization: `Bearer ${this.$store.state.token}` },
      };
      if (this.$store.user) {
      this.report.reportingUser = this.$store.state.user.id;
      }
      reportingService.add(this.report, config).then((response) => {
        if (response.status == 201) {
          alert('Report number: ' + response.data.pothole.potholeId + ' successfully entered. Thanks for reporting a pothole.');
          
          if(!this.$store.user){
            this.$router.push({ name: "home" });
          }
          else{
            alert("Ask anonymous user to register or allow to report another pothole");
            }
          this.clearForm();
        }
      }).catch(() => {alert("Invalid address, please try again.")});
    },
    clearForm() {
      this.report.pothole.address = {};
    },
  },
  computed: {
    isAddressFilledIn() {
      Object.values(this.report.pothole.address).forEach((element) => {
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
