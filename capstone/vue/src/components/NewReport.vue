<template>
  <form v-on:submit.prevent="saveReport">
    <label for="house-number">House Number</label>
    <input v-model="report.houseNumber" id="houser-number" type="text" />

    <label for="street-name">Street Name</label>
    <input v-model="report.streetName" id="street-name" type="text" />

    <label for="city">City</label>
    <input v-model="report.city" id="city" type="text" />

    <label for="state">State</label>
    <input v-model="report.state" id="state" type="text" />

    <label for="zip-code">Zip Code</label>
    <input v-model="report.zip" id="zip-code" type="text" />

    <input type="submit" value="Submit Report" />
    <button v-on:click.prevent="clearForm" value="Clear Form" />
  </form>
</template>

<script>
import reportingService from "@/services/ReportingService.js";

export default {
  name: "new-report",
  data() {
    return {
      report: {
        houseNumber: "",
        streetName: "",
        city: "",
        state: "",
        zip: "",
      },
    };
  },
  methods: {
    validateReport() {
      // TODO: Validate input strings
    },
    saveReport() {
      reportingService.add(this.report).then((response) => {
        if (response.status == 201) {
          this.clearForm();
          this.$router.push({ name: "home" });
        }
      });
    },
    clearForm() {
      this.report = {};
    },
  },
};
</script>

<style></style>
