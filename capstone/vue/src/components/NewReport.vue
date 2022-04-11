<template>
  <form v-on:submit.prevent="saveReport">
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

    <input type="submit" value="Submit Report" />
    <input type="button" v-on:click.prevent="clearForm" value="Clear Form" />
  </form>
</template>

<script>
import reportingService from "@/services/ReportingService.js";

export default {
  name: "new-report",
  data() {
    return {
      report: {
        reportingUser: "",
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
    isAddressFilledIn() {
      for (const field of this.report.pothole.address)
        if (field == "") return false;

      return true;
      // TODO: Validate input strings
      // return for (this.report.pothole.address
    },
    saveReport() {
      if (this.isAddressFilledIn) {
        const config = {
          headers: { Authorization: `Bearer ${this.$store.currentToken}` },
        };
        reportingService.add(this.report, config).then((response) => {
          if (response.status == 201) {
            this.clearForm();
            this.$router.push({ name: "home" });
          }
        });
      } else {
        alert("Idiot! Fill out the entire form before you submit");
      }
    },
    clearForm() {
      this.report.pothole.address = {};
    },
  },
};
</script>

<style></style>
