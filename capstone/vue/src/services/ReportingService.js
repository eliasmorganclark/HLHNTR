import axios from "axios";

const http = axios.create({
  baseURL: "http://localhost:8080",
});

export default {
  // Do not know the endpoint for this report yet
  // Consult backend team
  add(report) {
    return http.post("/newreport", report);
  },
};
