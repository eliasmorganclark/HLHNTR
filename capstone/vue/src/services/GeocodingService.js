import axios from "axios";

const http = axios.create({
  baseURL: "https://maps.googleapis.com/maps/api/geocode/json?latlng=",
});

const API_KEY = "AIzaSyDA6q7z3LGSNq75xjimeqNAOoChi3Ng1qw";

export default {
  getFormattedAddress(latlng) {
    this.reverseGeocode(latlng).then(response=>{
        const addressComponents = response.data.results.address_components;
        return {
            houseNumber: addressComponents[0].short_name,
            streetName: addressComponents[1].short_name,
            city: addressComponents[2].short_name,
            state: addressComponents[4].short_name,
            zip: addressComponents[6].short_name,
        }
    })
  },
  reverseGeocode(latlng){
    return http.get(latlng.lat + "," + latlng.lng + "&result_type=street_address&key" + API_KEY);
  }

};

