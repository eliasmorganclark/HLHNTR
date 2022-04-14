import axios from "axios";

const http = axios.create({
  baseURL: "http://localhost:8080",
});

export default {
  getAllReports() {
    return http.get("/getAllReports");
  },
  getAllPotholes(){
    return http.get("/getAllPotholes");
  },
  getAllDrains(){
    return http.get("/getAllDrains");
  }
};
