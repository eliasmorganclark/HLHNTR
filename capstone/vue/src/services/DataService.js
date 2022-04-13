import axios from "axios";

export default {
  getReports() {
    return axios.get("/getAllReports");
  }
};
