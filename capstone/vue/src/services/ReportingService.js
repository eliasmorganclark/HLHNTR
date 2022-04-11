import axios from "axios";

const http = axios.create({
  baseURL: "http://localhost:8080",
});

export default {
  add(report, config) {
    return http.post("/newreport", report, config);
  },
};
