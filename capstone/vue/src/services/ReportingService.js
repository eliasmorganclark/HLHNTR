import axios from "axios";

const http = axios.create({
  baseURL: "http://localhost:8080",
});

export default {
  addNewPotholeReport(hazard, config) {
    return http.post("/newPotholeReport", hazard, config);
  },

  addNewDrainReport(hazard, config) {
    return http.post("/newDrainReport", hazard, config);
  },
};
