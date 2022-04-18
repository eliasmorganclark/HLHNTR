import axios from "axios";

const googleReverseGeocode = axios.create({
  baseURL: "https://maps.googleapis.com/maps/api/geocode",
});
delete googleReverseGeocode.defaults.headers.common['Authorization'];

const API_KEY = "AIzaSyDA6q7z3LGSNq75xjimeqNAOoChi3Ng1qw";

export default {
  reverseGeocode(latlng){
    console.log("/json?latlng=" + latlng.lat + "," + latlng.lng + "&result_type=street_address&key=" + API_KEY);
    return googleReverseGeocode.get("/json?latlng=" + latlng.lat + "," + latlng.lng + "&result_type=street_address&key=" + API_KEY);
  }
};

