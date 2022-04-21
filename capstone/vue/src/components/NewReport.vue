<template>
  <div class="new-report-container">
    <div class="new-report-title-container">
      <h3 class="new-report-title-text">
        <img src="../img/logo.png" /> REPORT A NEW ROAD HAZARD
      </h3>
      <h2 class="new-report-title-text">
        Thank you for helping to keep our streets safe! Please enter the address
        of the hazard you'd like to report below:
      </h2>
    </div>
    <div class="new-report-form-container">
      <form class="new-report-form" v-on:submit.prevent="saveNewReport">
        <div class="hazard-type-container">
          <label for="hazard-type">Hazard Type</label>
          <select
            v-model="hazardType"
            name="hazard-type"
            id="hazard-type"
            class="Selecty"
          >
            <option value="">PLEASE SELECT</option>
            <option value="POTHOLE">POTHOLE</option>
            <option value="DRAIN">STORM DRAIN</option>
          </select>
        </div>

        <div v-if="hazardType == 'POTHOLE'" class="severity-container">
          <label for="severity">Severity</label>
          <select
            v-model="hazard.severity"
            name="severity"
            id="severity"
            class="Selecty"
          >
            <option value="">PLEASE SELECT</option>
            <option value="LOW">LOW</option>
            <option value="MODERATE">MODERATE</option>
            <option value="HIGH">HIGH</option>
          </select>
        </div>

        <div class="street-info">
          <label for="house-number">House Number</label>
          <input
            v-model.trim="hazard.address.houseNumber"
            id="houser-number"
            type="text"
          />
          <Br />
          <label for="street-name">Street Name</label>
          <input
            v-model.trim="hazard.address.streetName"
            id="street-name"
            type="text"
          />
          <label for="street-type">Street Type</label>
          <select
            v-model="streetType"
            name="street-type"
            id="street-type"
            class="Selecty"
          >
            <option
              v-for="type in streetTypes"
              :key="type.suffix"
              v-bind:streetType="streetType"
            >
              {{ type.standardAbbr }}
            </option>
            <!-- <option value="">---</option>
            <option value="AVE">AVE</option>
            <option value="ST">ST</option>
            <option value="BLVD">BLVD</option> -->
          </select>
        </div>

        <br />
        <label for="city">City</label>
        <input v-model.trim="hazard.address.city" id="city" type="text" />
        <label for="state">State</label>
        <select
          id="state"
          name="state"
          v-model="hazard.address.state"
          class="Selecty"
        >
          <option value="OH">Ohio</option>
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

        <div class="picture-submit-clear">
          <div id="center-this">
            <label for="file">Upload a Picture</label>
            <input
              type="file"
              @change="setImageFile"
              ref="file"
              accept="image/jpeg"
            />
          </div>
          <div id="center-this">
            <input
              :disabled="isLoading"
              class="submitty"
              type="submit"
              value="Submit Report"
            />
            <input
              class="submitty"
              type="button"
              v-on:click.prevent="clearForm"
              value="Clear Form"
            />
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import reportingService from "@/services/ReportingService.js";
import fileUploadService from "@/services/FileUploadService.js";

export default {
  name: "new-report",

  props: {
    propHazard: {},
  },
  emits: ["new-hazard-id"],
  watch: {
    // eslint-disable-next-line no-unused-vars
    propHazard(newHazard, oldHazard) {
      this.setDataFromProp();
    },
  },
  data() {
    return {
      isLoading: false,
      image: null,
      imageHazardId: "",
      streetTypes: [],
      hazardType: "",
      streetType: "",
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
      this.isLoading = true;
      this.hazard.address.streetName += " " + this.streetType;
      console.log(this.hazard);
      const config = {
        headers: { Authorization: `Bearer ${this.$store.state.token}` },
      };
      if (this.$store.state.user) {
        this.hazard.reportingUser = this.$store.state.user.id;
      }
      reportingService
        .addNewPotholeReport(this.hazard, config)
        .then((response) => {
          if (response.status == 201) {
            this.imageHazardId = response.data.pothole.hazardId;
            alert(
              "Pothole number: " +
                response.data.pothole.hazardId +
                " successfully entered. Thanks for reporting a pothole."
            );
            this.submitFile();
            this.emitNewHazard(response.data.pothole.hazardId);
            if (!this.$store.user) {
              this.$router.push({ name: "home" });
            } else {
              alert(
                "Ask anonymous user to register or allow to report another pothole"
              );
            }
            this.clearForm();
            this.isLoading = false;
          }
        })
        .catch(() => {
          alert("Invalid address, please try again.");
          this.isLoading = false;
        });
    },
    saveNewDrainReport() {
      this.isLoading = true;
      this.hazard.address.streetName += " " + this.streetType;
      const config = {
        headers: { Authorization: `Bearer ${this.$store.state.token}` },
      };
      if (this.$store.state.user) {
        this.hazard.reportingUser = this.$store.state.user.id;
      }
      reportingService
        .addNewDrainReport(this.hazard, config)
        .then((response) => {
          if (response.status == 201) {
            this.imageHazardId = response.data.pothole.hazardId;
            alert(
              "Drain number: " +
                response.data.drain.hazardId +
                " successfully entered. Thanks for reporting a drain."
            );
            this.submitFile();
            this.emitNewHazard(response.data.pothole.hazardId);
            if (!this.$store.user) {
              this.$router.push({ name: "home" });
            } else {
              alert(
                "Ask anonymous user to register or allow to report another drain"
              );
            }
            this.clearForm();
            this.isLoading = false;
          }
        })
        .catch(() => {
          alert("Invalid address, please try again.");
          this.isLoading = false;
        });
    },
    setImageFile() {
      this.image = this.$refs.file.files[0];
      console.log(this.image);
    },
    submitFile() {
      if (this.image) {
        const formData = new FormData();
        formData.append("file", this.image);
        formData.append("hazardId", this.imageHazardId);
        console.log(this.hazardId);
        fileUploadService
          .uploadPhoto(formData)
          .then((response) => {
            console.log(response);
            alert("Image uploaded successfully");
            this.imageHazardId = "";
            this.image = null;
          })
          .catch((error) => {
            console.log(error);
            alert("There was a problem uploading your image");
          });
      }
    },
    clearForm() {
      this.hazard.address = {};
      this.image = null;
    },
    setDataFromProp() {
      console.table(this.propHazard);
      this.hazardType = this.propHazard.hazardType;
      this.hazard.address = this.propHazard.address;
    },
    emitNewHazard(hazardId) {
      this.$emit("new-hazard-id", hazardId);
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
    newReportMade() {
      return this.newReportHazardId != "";
    },
  },
  created() {
    this.streetTypes = require("street-types");
  },
};
</script>

<style>
h2 {
  font-family: "Kanit", sans-serif;
  letter-spacing: 2px;
}

#body {
  font-family: "Kanit", sans-serif;
  background-image: url("../img/potholebg.jpg");

  background-color: black;
}

.hazard-type-container {
  grid-area: hazard-type;
}

.new-report-form-container {
  /* grid-area: form; */
  /* display: flex; */
  /* flex-direction: column; */
}

.new-report-container {
  font-family: "Kanit", sans-serif;
  display: grid;
  /* grid-template-columns: 1fr 1fr; */
  grid-template-areas:
    "title"
    "picture-submit-clear   ";
  align-items: center;
  justify-content: center;
  /* justify-items: center; */

  margin: 10px;
  padding: 20px;
  background-color: #ccc;
  border-radius: 10px;
}
.new-report-title-container {
  grid-area: title;
}

h3 {
  text-align: center;
  font-family: "Kanit", sans-serif;
  font-style: italic;
  color: black;
  text-shadow: 2px 2px fuchsia;
  letter-spacing: 3px;
  font-size: 35px;
  margin: 5px 0;
}
img {
  margin: 3px 0 -17px 0;
  height: 60px;
  width: auto;
}
h2 {
  margin: 0 30px;
  font-family: "Kanit", sans-serif;
  font-weight: 400;
  font-size: 15px;
  color: #333;
  text-align: center;
}

input {
  font-family: "Kanit", sans-serif;
  letter-spacing: 1px;
  width: 300px;
  padding: 8px;
  margin: 8px;
  border: 3px solid #ccc;
  border-radius: 10px;
  background-color: white;
  color: fuchsia;
}

.Selecty {
  font-family: "Kanit", sans-serif;
  letter-spacing: 1px;
  width: 300px;
  padding: 8px;
  margin: 8px;
  border: 3px solid #ccc;
  border-radius: 10px;
  background-color: white;
  color: fuchsia;
}

.picture-submit-clear {
  grid-area: picture-submit-clear;
}

a {
  text-transform: uppercase;
  color: FUCHSIA;
  text-decoration: none;
}

.submitty {
  letter-spacing: 3px;
  font-family: "Kanit", sans-serif;
  font-size: 20px;
  text-transform: uppercase;
  border: 0;
  box-shadow: none;
  background-color: black;
  color: fuchsia;
  padding: 5px;
  margin: 20px;
  width: 200px;
  border-radius: 10px;
}

#center-this {
  text-align: center;
}
</style>
