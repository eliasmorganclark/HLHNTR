import axios from "axios";

export default {
  getAllReports() {
    return axios.get("/getAllReports");
  },
};
