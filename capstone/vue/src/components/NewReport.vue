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
    
    saveReport() {
      if (this.isAddressFilledIn) {
        const config = {
          headers: { Authorization: `Bearer ${this.$store.state.token}` },
        };
        this.report.reportingUser = this.$store.state.user.id;
        reportingService.add(this.report, config).then((response) => {
          if (response.status == 400) {
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
  computed: {
isAddressFilledIn() {
      for (const field of this.report.pothole.address) {
        if (field === "") {
          return false;
        }
      }
      return true;
      
      // TODO: Validate input strings
      // return for (this.report.pothole.address
    },
  }
};
</script>

<style></style>
