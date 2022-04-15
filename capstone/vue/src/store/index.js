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
    drains: []
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
    }
  },
  getters:{
    getAllHazards (state){
      const allHazards = [];
      state.drains.forEach( (drain) => {
        drain.iconPath = 'https://i.ibb.co/27qGbDF/stormdrain.png'; 
        allHazards.push(drain);
      });
      state.potholes.forEach( (pothole) => {
        pothole.iconPath = 'https://i.ibb.co/KN20TXv/pothole.png';
        allHazards.push(pothole);
      });
      return allHazards;
    }
  }
});

