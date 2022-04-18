import axios from "axios";

const http = axios.create({
  baseURL: "http://localhost:8080",
});

export default {
  getAllReports() {
    return http.get("/report/all");
  },

  getReport(reportId) {
    return http.get(`/report/${reportId}`);
  },

  getReportsForHazard(hazardId) {
    return http.get(`/report/all/${hazardId}`);
  },

  getAllHazards() {
    return http.get("/hazard/all");
  },

  getHazard(hazardId) {
    return http.get(`/hazard/${hazardId}`);
  },

  getAllPotholes() {
    return http.get("/pothole/all");
  },

  getPothole(hazardId) {
    return http.get(`/pothole/${hazardId}`);
  },

  getAllDrains() {
    return http.get("/drain/all");
  },

  getDrain(hazardId) {
    return http.get(`/drain/${hazardId}`);
  },
  
  updateDrain(hazard){
    return http.put("/drain/",hazard)
  },

  updatePothole(hazard){
    return http.put("/drain/",hazard)
  }

};
