import axios from "axios";

const googleReverseGeocode = axios.create({
  baseURL: "https://maps.googleapis.com/maps/api/geocode",
});
delete googleReverseGeocode.defaults.headers.common['Authorization'];

const API_KEY = "AIzaSyDA6q7z3LGSNq75xjimeqNAOoChi3Ng1qw";

export default {
  getFormattedAddress(latlng) {
    console.log('latlng:' + latlng)
    this.reverseGeocode(latlng).then(response=>{
        const addressComponents = response.data.results[0].address_components;
        console.log(addressComponents);
        let address = {
            houseNumber: addressComponents[0].short_name,
            streetName: addressComponents[1].short_name,
            city: addressComponents[2].short_name,
            state: addressComponents[4].short_name,
            zip: addressComponents[6].short_name,
        };
        console.log(address);
        return address;
    })
  },
  reverseGeocode(latlng){
    console.log("/json?latlng=" + latlng.lat + "," + latlng.lng + "&result_type=street_address&key=" + API_KEY);
    return googleReverseGeocode.get("/json?latlng=" + latlng.lat + "," + latlng.lng + "&result_type=street_address&key=" + API_KEY);
  }

};

