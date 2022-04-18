import axios from "axios";

const http = axios.create({
  baseURL: "http://localhost:8080",
});

export default {
  addNewPotholeReport(hazard, config) {
    return http.post("/pothole", hazard, config);
  },

  addNewDrainReport(hazard, config) {
    return http.post("/drain", hazard, config);
  },
};
