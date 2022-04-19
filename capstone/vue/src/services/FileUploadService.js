import axios from "axios";

const uploadUrl = axios.create({
  baseURL: "http://localhost:8080",
});


export default {
    uploadPhoto(formData) {
        console.log(formData);
        return uploadUrl.post("/upload",formData, {headers: {
    "Content-Type": "multipart/form-data",'Access-Control-Allow-Origin': '*'
  }});
    },
};
