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
        <select id="state" name="state" v-model="hazard.address.state">
          <option value="AL">Alabama</option>
          <option value="AK">Alaska</option>
          <option value="AZ">Arizona</option>
          <option value="AR">Arkansas</option>
          <option value="CA">California</option>
          <option value="CO">Colorado</option>
          <option value="CT">Connecticut</option>
          <option value="DE">Delaware</option>
          <option value="FL">Florida</option>
          <option value="GA">Georgia</option>
          <option value="HI">Hawaii</option>
          <option value="ID">Idaho</option>
          <option value="IL">Illinois</option>
          <option value="IN">Indiana</option>
          <option value="IA">Iowa</option>
          <option value="KS">Kansas</option>
          <option value="KY">Kentucky</option>
          <option value="LA">Louisiana</option>
          <option value="ME">Maine</option>
          <option value="MD">Maryland</option>
          <option value="MA">Massachusetts</option>
          <option value="MI">Michigan</option>
          <option value="MN">Minnesota</option>
          <option value="MS">Mississippi</option>
          <option value="MO">Missouri</option>
          <option value="MT">Montana</option>
          <option value="NE">Nebraska</option>
          <option value="NV">Nevada</option>
          <option value="NH">New Hampshire</option>
          <option value="NJ">New Jersey</option>
          <option value="NM">New Mexico</option>
          <option value="NY">New York</option>
          <option value="NC">North Carolina</option>
          <option value="ND">North Dakota</option>
          <option value="OH">Ohio</option>
          <option value="OK">Oklahoma</option>
          <option value="OR">Oregon</option>
          <option value="PA">Pennsylvania</option>
          <option value="RI">Rhode Island</option>
          <option value="SC">South Carolina</option>
          <option value="SD">South Dakota</option>
          <option value="TN">Tennessee</option>
          <option value="TX">Texas</option>
          <option value="UT">Utah</option>
          <option value="VT">Vermont</option>
          <option value="VA">Virginia</option>
          <option value="WA">Washington</option>
          <option value="WV">West Virginia</option>
          <option value="WI">Wisconsin</option>
          <option value="WY">Wyoming</option>
        </select>

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
        severity: "",
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
