import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
// import { all } from "core-js/fn/promise";

Vue.use(Vuex);

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem("token");
const currentUser = JSON.parse(localStorage.getItem("user"));

if (currentToken != null) {
  axios.defaults.headers.common["Authorization"] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || "",
    user: currentUser || {},
    reports: [],
    potholes: [],
    drains: [],
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem("token", token);
      axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem("user", JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem("token");
      localStorage.removeItem("user");
      state.token = "";
      state.user = {};
      axios.defaults.headers.common = {};
    },
    LOAD_REPORTS(state, reports) {
      state.reports = reports;
    },
    LOAD_POTHOLES(state, potholes) {
      state.potholes = potholes;
    },
    LOAD_DRAINS(state, drains) {
      state.drains = drains;
    },
  },
  getters: {
    getAllHazards(state) {
      const allHazards = [];
      state.drains.forEach((drain) => {
        drain.iconPath = "https://i.ibb.co/27qGbDF/stormdrain.png";
        allHazards.push(drain);
      });
      state.potholes.forEach((pothole) => {
        pothole.iconPath = "https://i.ibb.co/KN20TXv/pothole.png";
        allHazards.push(pothole);
      });
      return allHazards;
    },
    getFilteredHazards(state, filter) {
      // Filter object is a model which includes all attributes of any hazard
      // includes both drain and pothole fields
      //
      //   {
      //     "hazardId": 1001, done
      //     "verified": false,
      //     "address": {
      //         "houseNumber": 1234,
      //         "streetName": "HOLE CIRCLE",
      //         "city": "AKRON",
      //         "state": "OH",
      //         "zip": 44444,
      //         "coordinates": null
      //     },
      //     "hazardType": "DRAIN",
      //     "repairStatus": "Not repaired",
      //     "reportingUser": null,
      //     "clogged": true
      //     "severity": "",
      //     // INSERT DATES HERE
      //     // INSERT FIELDS HERE
      // }
      let filteredHazards = this.getAllHazards;
      // Check filter
      if (filter.hazardId != null) {
        filteredHazards = filteredHazards.filter(
          (element) => element.hazardId == filter.hazardId
        );
      }

      if (filter.verified != null) {
        filteredHazards = filteredHazards.filter(
          (element) => element.verified == filter.verified
        );
      }
      if (filter.address != null) {
        //         "houseNumber": 1234,
        if (filter.address.houseNumber != null) {
          filteredHazards = filteredHazards.filter(
            (element) =>
              element.address.houseNumber == filter.address.houseNumber
          );
        }
        //         "streetName": "HOLE CIRCLE",
        if (filter.address.streetName != null) {
          filteredHazards = filteredHazards.filter(
            (element) => element.address.streetName == filter.address.streetName
          );
        }
        //         "city": "AKRON",
        if (filter.address.city != null) {
          filteredHazards = filteredHazards.filter(
            (element) => element.address.city == filter.address.city
          );
        }
        //         "state": "OH",
        if (filter.address.state != null) {
          filteredHazards = filteredHazards.filter(
            (element) => element.address.state == filter.address.state
          );
        }
        //         "zip": 44444,
        if (filter.address.zip != null) {
          filteredHazards = filteredHazards.filter(
            (element) => element.address.zip == filter.address.zip
          );
        }
        //         "coordinates": null
        if (filter.address.coordinates != null) {
          if (filter.address.coordinates.lat) {
            filteredHazards = filteredHazards.filter(
              (element) =>
                element.address.coordinates.lat ==
                filter.address.coordinates.lat
            );
          }
          if (filter.address.coordinates.lng) {
            filteredHazards = filteredHazards.filter(
              (element) =>
                element.address.coordinates.lng ==
                filter.address.coordinates.lng
            );
          }
        }
      }

      if (filter.hazardType != null) {
        filteredHazards = filteredHazards.filter(
          (element) => element.hazardType == filter.hazardType
        );
      }
      if (filter.reportingUser != null) {
        filteredHazards = filteredHazards.filter(
          (element) => element.reportingUser == filter.reportingUser
        );
      }
      if (filter.clogged != null) {
        filteredHazards = filteredHazards.filter(
          (element) => element.clogged == filter.clogged
        );
      }
      if (filter.severity != null) {
        filteredHazards = filteredHazards.filter(
          (element) => element.severity == filter.severity
        );
      }
      return filteredHazards;
    },
  },
});
