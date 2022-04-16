import axios from "axios";

const http = axios.create({
  baseURL: "http://localhost:8080",
});


export default {
  getAllReports() {
    return http.get("/getAllReports");
  },

  getReport(reportId){
    return http.get(`/report/${reportId}`);
  },

  getReportsForHazard(hazardId){
    return http.get(`/getAllReports/${hazardId}`);
  },

  getAllPotholes(){
    return http.get("/getAllPotholes");
  },
  
  getPothole(hazardId){
    return http.get(`/pothole/${hazardId}`);
  },

  getAllDrains(){
    return http.get("/getAllDrains");
  },

  getDrain(hazardId){
    return http.get(`/drain/${hazardId}`);
  }

  
};
